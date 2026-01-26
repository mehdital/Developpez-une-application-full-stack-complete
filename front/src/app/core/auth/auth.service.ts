import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable, firstValueFrom, tap } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AuthResponse } from '../models/auth.model';
import { User } from '../models/user.model';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly tokenKey = 'mdd_token';
  private readonly userSubject = new BehaviorSubject<User | null>(null);

  readonly user$ = this.userSubject.asObservable();

  constructor(private readonly http: HttpClient, private readonly router: Router) {}

  get token(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  get userSnapshot(): User | null {
    return this.userSubject.value;
  }

  async restoreSession(): Promise<void> {
    if (!this.token) {
      return;
    }
    try {
      const user = await firstValueFrom(this.http.get<User>(`${environment.apiUrl}/api/users/me`));
      this.userSubject.next(user);
    } catch {
      this.clearSession();
    }
  }

  refreshMe(): Observable<User> {
    return this.http.get<User>(`${environment.apiUrl}/api/users/me`).pipe(tap((u) => this.userSubject.next(u)));
  }

  login(identifier: string, password: string): Observable<AuthResponse> {
    return this.http
      .post<AuthResponse>(`${environment.apiUrl}/api/auth/login`, { identifier, password })
      .pipe(
        tap((res) => {
          localStorage.setItem(this.tokenKey, res.token);
          this.userSubject.next(res.user);
        })
      );
  }

  register(username: string, email: string, password: string): Observable<User> {
    return this.http.post<User>(`${environment.apiUrl}/api/auth/register`, { username, email, password });
  }

  logout(): void {
    this.clearSession();
    this.router.navigateByUrl('/');
  }

  clearSession(): void {
    localStorage.removeItem(this.tokenKey);
    this.userSubject.next(null);
  }
}

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { User } from '../models/user.model';

@Injectable({ providedIn: 'root' })
export class UserService {
  constructor(private readonly http: HttpClient) {}

  me(): Observable<User> {
    return this.http.get<User>(`${environment.apiUrl}/api/users/me`);
  }

  updateMe(username: string, email: string, password: string): Observable<User> {
    return this.http.put<User>(`${environment.apiUrl}/api/users/me`, { username, email, password });
  }
}


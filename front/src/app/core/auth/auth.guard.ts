import { Injectable } from '@angular/core';
import { CanActivateChild, Router } from '@angular/router';
import { AuthService } from './auth.service';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivateChild {
  constructor(private readonly auth: AuthService, private readonly router: Router) {}

  canActivateChild(): boolean {
    if (this.auth.token) {
      return true;
    }
    this.router.navigateByUrl('/');
    return false;
  }
}


import { Component } from '@angular/core';
import { AuthService } from 'src/app/core/auth/auth.service';

@Component({
  selector: 'app-shell',
  templateUrl: './shell.component.html',
  styleUrls: ['./shell.component.scss'],
})
export class ShellComponent {
  constructor(readonly auth: AuthService) {}

  initials(username: string | undefined | null): string {
    const value = (username || '').trim();
    if (!value) {
      return '?';
    }
    return value.slice(0, 2).toUpperCase();
  }

  closeDrawer(navToggle: HTMLInputElement): void {
    navToggle.checked = false;
  }
}


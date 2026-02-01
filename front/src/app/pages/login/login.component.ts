import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { finalize } from 'rxjs';
import { AuthService } from 'src/app/core/auth/auth.service';
import { ApiError } from 'src/app/core/models/api-error.model';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
})
export class LoginComponent {
  loading = false;
  formError = '';
  fieldErrors: Record<string, string> = {};

  readonly form = this.fb.nonNullable.group({
    identifier: ['', [Validators.required]],
    password: ['', [Validators.required]],
  });

  constructor(private readonly fb: FormBuilder, private readonly auth: AuthService, private readonly router: Router) {}

  submit(): void {
    this.formError = '';
    this.fieldErrors = {};

    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const { identifier, password } = this.form.getRawValue();
    this.loading = true;

    this.auth
      .login(identifier, password)
      .pipe(finalize(() => (this.loading = false)))
      .subscribe({
        next: () => this.router.navigateByUrl('/articles'),
        error: (err) => this.applyApiError(err?.error as ApiError),
      });
  }

  private applyApiError(error: ApiError | undefined): void {
    this.formError = error?.message || 'Une erreur est survenue';
    this.fieldErrors = error?.fieldErrors || {};
  }
}


import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { finalize } from 'rxjs';
import { AuthService } from 'src/app/core/auth/auth.service';
import { ApiError } from 'src/app/core/models/api-error.model';
import { TopicSummary } from 'src/app/core/models/topic.model';
import { User } from 'src/app/core/models/user.model';
import { TopicService } from 'src/app/core/services/topic.service';
import { UserService } from 'src/app/core/services/user.service';

const OPTIONAL_PASSWORD_REGEX = /^(?:$|(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^A-Za-z\d]).{8,})$/;

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
})
export class ProfileComponent implements OnInit {
  loading = false;
  saving = false;
  error = '';
  user: User | null = null;
  unsubscribingIds = new Set<number>();

  readonly form = this.fb.nonNullable.group({
    username: ['', [Validators.required]],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.pattern(OPTIONAL_PASSWORD_REGEX)]],
  });

  constructor(
    private readonly fb: FormBuilder,
    private readonly userService: UserService,
    private readonly topicService: TopicService,
    private readonly auth: AuthService
  ) {}

  ngOnInit(): void {
    this.loadMe();
  }

  loadMe(): void {
    this.loading = true;
    this.error = '';
    this.userService
      .me()
      .pipe(finalize(() => (this.loading = false)))
      .subscribe({
        next: (user) => {
          this.user = user;
          this.form.reset({ username: user.username, email: user.email, password: '' });
        },
        error: (err) => this.applyError(err?.error as ApiError),
      });
  }

  save(): void {
    this.form.markAllAsTouched();
    if (this.form.invalid) {
      return;
    }

    const { username, email, password } = this.form.getRawValue();
    this.saving = true;
    this.error = '';

    this.userService
      .updateMe(username, email, password)
      .pipe(finalize(() => (this.saving = false)))
      .subscribe({
        next: (user) => {
          this.user = user;
          this.form.reset({ username: user.username, email: user.email, password: '' });
          this.auth.refreshMe().subscribe();
        },
        error: (err) => this.applyError(err?.error as ApiError),
      });
  }

  unsubscribe(topic: TopicSummary): void {
    this.unsubscribingIds.add(topic.id);
    this.topicService
      .unsubscribe(topic.id)
      .pipe(finalize(() => this.unsubscribingIds.delete(topic.id)))
      .subscribe({
        next: () => {
          this.loadMe();
          this.auth.refreshMe().subscribe();
        },
        error: (err) => this.applyError(err?.error as ApiError),
      });
  }

  private applyError(error: ApiError | undefined): void {
    this.error = error?.message || 'Une erreur est survenue';
  }
}


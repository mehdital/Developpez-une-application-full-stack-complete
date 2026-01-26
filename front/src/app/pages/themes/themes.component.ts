import { Component, OnInit } from '@angular/core';
import { finalize } from 'rxjs';
import { AuthService } from 'src/app/core/auth/auth.service';
import { ApiError } from 'src/app/core/models/api-error.model';
import { Topic } from 'src/app/core/models/topic.model';
import { TopicService } from 'src/app/core/services/topic.service';

@Component({
  selector: 'app-themes',
  templateUrl: './themes.component.html',
  styleUrls: ['./themes.component.scss'],
})
export class ThemesComponent implements OnInit {
  loading = false;
  error = '';
  topics: Topic[] = [];
  subscribingIds = new Set<number>();

  constructor(private readonly topicService: TopicService, private readonly auth: AuthService) {}

  ngOnInit(): void {
    this.loadTopics();
  }

  loadTopics(): void {
    this.loading = true;
    this.error = '';

    this.topicService
      .listTopics()
      .pipe(finalize(() => (this.loading = false)))
      .subscribe({
        next: (topics) => (this.topics = topics),
        error: (err) => this.applyError(err?.error as ApiError),
      });
  }

  subscribe(topic: Topic): void {
    this.subscribingIds.add(topic.id);
    this.topicService
      .subscribe(topic.id)
      .pipe(finalize(() => this.subscribingIds.delete(topic.id)))
      .subscribe({
        next: () => {
          topic.subscribed = true;
          this.auth.refreshMe().subscribe();
        },
        error: (err) => this.applyError(err?.error as ApiError),
      });
  }

  private applyError(error: ApiError | undefined): void {
    this.error = error?.message || 'Une erreur est survenue';
  }
}


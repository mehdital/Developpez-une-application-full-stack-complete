import { Component, OnInit } from '@angular/core';
import { finalize } from 'rxjs';
import { ApiError } from 'src/app/core/models/api-error.model';
import { PostFeedItem } from 'src/app/core/models/post.model';
import { PostService } from 'src/app/core/services/post.service';

@Component({
  selector: 'app-articles',
  templateUrl: './articles.component.html',
  styleUrls: ['./articles.component.scss'],
})
export class ArticlesComponent implements OnInit {
  loading = false;
  error = '';

  sort: 'desc' | 'asc' = 'desc';
  posts: PostFeedItem[] = [];

  constructor(private readonly postService: PostService) {}

  ngOnInit(): void {
    this.loadFeed();
  }

  loadFeed(): void {
    this.loading = true;
    this.error = '';

    this.postService
      .getFeed(this.sort)
      .pipe(finalize(() => (this.loading = false)))
      .subscribe({
        next: (posts) => (this.posts = posts),
        error: (err) => this.applyError(err?.error as ApiError),
      });
  }

  onSortChange(value: string): void {
    this.sort = value === 'asc' ? 'asc' : 'desc';
    this.loadFeed();
  }

  excerpt(content: string): string {
    const trimmed = (content || '').trim();
    return trimmed.length > 160 ? `${trimmed.slice(0, 160)}…` : trimmed;
  }

  private applyError(error: ApiError | undefined): void {
    this.error = error?.message || 'Une erreur est survenue';
  }
}


import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { finalize } from 'rxjs';
import { ApiError } from 'src/app/core/models/api-error.model';
import { PostDetail } from 'src/app/core/models/post.model';
import { PostService } from 'src/app/core/services/post.service';

@Component({
  selector: 'app-article-detail',
  templateUrl: './article-detail.component.html',
})
export class ArticleDetailComponent implements OnInit {
  loading = false;
  sending = false;
  error = '';
  post: PostDetail | null = null;

  readonly commentForm = this.fb.nonNullable.group({
    content: ['', [Validators.required]],
  });

  constructor(private readonly fb: FormBuilder, private readonly route: ActivatedRoute, private readonly postService: PostService) {}

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (!id) {
      this.error = 'Article introuvable';
      return;
    }

    this.loading = true;
    this.error = '';

    this.postService
      .getPost(id)
      .pipe(finalize(() => (this.loading = false)))
      .subscribe({
        next: (post) => (this.post = post),
        error: (err) => this.applyError(err?.error as ApiError),
      });
  }

  sendComment(): void {
    if (!this.post) {
      return;
    }

    this.commentForm.markAllAsTouched();
    if (this.commentForm.invalid) {
      return;
    }

    const content = this.commentForm.getRawValue().content.trim();
    if (!content) {
      return;
    }

    this.sending = true;
    this.postService
      .addComment(this.post.id, content)
      .pipe(finalize(() => (this.sending = false)))
      .subscribe({
        next: (comment) => {
          this.post?.comments.push(comment);
          this.commentForm.reset({ content: '' });
        },
        error: (err) => this.applyError(err?.error as ApiError),
      });
  }

  badge(username: string): string {
    const v = (username || '').trim();
    return v ? v.slice(0, 1).toUpperCase() : '?';
  }

  private applyError(error: ApiError | undefined): void {
    this.error = error?.message || 'Une erreur est survenue';
  }
}


import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { finalize } from 'rxjs';
import { ApiError } from 'src/app/core/models/api-error.model';
import { Topic } from 'src/app/core/models/topic.model';
import { PostService } from 'src/app/core/services/post.service';
import { TopicService } from 'src/app/core/services/topic.service';

@Component({
  selector: 'app-new-article',
  templateUrl: './new-article.component.html',
  styleUrls: ['./new-article.component.scss'],
})
export class NewArticleComponent implements OnInit {
  loading = false;
  error = '';
  topics: Topic[] = [];

  readonly form = this.fb.group({
    topicId: [null as number | null, [Validators.required]],
    title: ['', [Validators.required]],
    content: ['', [Validators.required]],
  });

  constructor(
    private readonly fb: FormBuilder,
    private readonly topicService: TopicService,
    private readonly postService: PostService,
    private readonly router: Router
  ) {}

  ngOnInit(): void {
    this.topicService.listTopics().subscribe({
      next: (topics) => (this.topics = topics),
      error: (err) => this.applyError(err?.error as ApiError),
    });
  }

  submit(): void {
    this.error = '';
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const { topicId, title, content } = this.form.getRawValue();
    this.loading = true;
    this.postService
      .createPost(topicId as number, title ?? '', content ?? '')
      .pipe(finalize(() => (this.loading = false)))
      .subscribe({
        next: (created) => this.router.navigate(['/articles', created.id]),
        error: (err) => this.applyError(err?.error as ApiError),
      });
  }

  private applyError(error: ApiError | undefined): void {
    this.error = error?.message || 'Une erreur est survenue';
  }
}


import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Comment, PostDetail, PostFeedItem } from '../models/post.model';

@Injectable({ providedIn: 'root' })
export class PostService {
  constructor(private readonly http: HttpClient) {}

  getFeed(sort: 'asc' | 'desc'): Observable<PostFeedItem[]> {
    return this.http.get<PostFeedItem[]>(`${environment.apiUrl}/api/posts`, { params: { sort } });
  }

  createPost(topicId: number, title: string, content: string): Observable<PostDetail> {
    return this.http.post<PostDetail>(`${environment.apiUrl}/api/posts`, { topicId, title, content });
  }

  getPost(postId: number): Observable<PostDetail> {
    return this.http.get<PostDetail>(`${environment.apiUrl}/api/posts/${postId}`);
  }

  addComment(postId: number, content: string): Observable<Comment> {
    return this.http.post<Comment>(`${environment.apiUrl}/api/posts/${postId}/comments`, { content });
  }
}


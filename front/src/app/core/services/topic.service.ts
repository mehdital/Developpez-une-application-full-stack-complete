import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Topic } from '../models/topic.model';

@Injectable({ providedIn: 'root' })
export class TopicService {
  constructor(private readonly http: HttpClient) {}

  listTopics(): Observable<Topic[]> {
    return this.http.get<Topic[]>(`${environment.apiUrl}/api/topics`);
  }

  subscribe(topicId: number): Observable<void> {
    return this.http.post<void>(`${environment.apiUrl}/api/topics/${topicId}/subscribe`, {});
  }

  unsubscribe(topicId: number): Observable<void> {
    return this.http.delete<void>(`${environment.apiUrl}/api/topics/${topicId}/subscribe`);
  }
}


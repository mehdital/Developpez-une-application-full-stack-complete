import { TopicSummary } from './topic.model';

export interface PostFeedItem {
  id: number;
  title: string;
  authorUsername: string;
  createdAt: string;
  content: string;
}

export interface Comment {
  id: number;
  authorUsername: string;
  createdAt: string;
  content: string;
}

export interface PostDetail {
  id: number;
  title: string;
  authorUsername: string;
  createdAt: string;
  topic: TopicSummary;
  content: string;
  comments: Comment[];
}


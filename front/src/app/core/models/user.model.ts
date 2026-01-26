import { TopicSummary } from './topic.model';

export interface User {
  id: number;
  username: string;
  email: string;
  subscriptions: TopicSummary[];
}


export interface TopicSummary {
  id: number;
  title: string;
  description: string;
}

export interface Topic extends TopicSummary {
  subscribed: boolean;
}


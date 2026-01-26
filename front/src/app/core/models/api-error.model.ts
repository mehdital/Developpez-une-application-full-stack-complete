export interface ApiError {
  message: string;
  fieldErrors?: Record<string, string>;
}


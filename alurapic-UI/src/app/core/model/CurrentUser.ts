import { User } from './User';

export interface CurrentUser {
  token: string;
  user: User;
}
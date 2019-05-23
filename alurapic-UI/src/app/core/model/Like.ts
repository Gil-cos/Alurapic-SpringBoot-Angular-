import { Photo } from './photo';
import { User } from './User';

export class Like {
  constructor(
    public id: number,
    public user: User,
    public photo: Photo
  ) { }
}
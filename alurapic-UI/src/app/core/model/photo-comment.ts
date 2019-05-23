import { NewUser } from './NewUser';
import { Photo } from './photo';

export interface PhotoComment {
    date: Date;
    user: NewUser;
    photo: Photo;
    text: string;
}
import { User } from 'src/app/core/model/User';

export interface Photo {
    id: number;
    postDate: Date;
    user: User;
    url: string;
    description: string;
    allowComments: boolean;
    likes: number;
    comments: number;
}
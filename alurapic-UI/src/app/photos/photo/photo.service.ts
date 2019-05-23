import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ResponseApi } from 'src/app/core/model/ResponseApi';
import { environment } from '../../../environments/environment';
import { NewComment } from "../../core/model/NewComment";
import { Photo } from "../../core/model/photo";
import { PhotoComment } from '../../core/model/photo-comment';
import { Like } from '../../core/model/Like';

const httpOptionsPhoto = {
    headers: new HttpHeaders({ 'enctype': 'multipart/form-data', 'observe': 'events', 'reportProgress': 'true' })
};

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

const API = environment.ApiUrl;

@Injectable({ providedIn: 'root' })
export class PhotoService {

    constructor(private http: HttpClient) { }

    listFromUser(userName: string) {
        return this.http
            .get<ResponseApi>(`${API}/api/photo/list/${userName}`);
    }

    listFromUserPaginated(userName: string) {
        return this.http
            .get<Photo[]>(`${API}/api/photo/list/${userName}`);
    }

    upload(description: string, allowComments: boolean, file: File) {

        const formData = new FormData();
        formData.append('description', description);
        formData.append('allowComments', allowComments ? 'true' : 'false');
        formData.append('imageFile', file);

        return this.http.post(
            API + '/api/photo', formData, httpOptionsPhoto
        );
    }

    findById(photoId: number) {

        return this.http.get<Photo>(`${API}/api/photo/${photoId}`);
    }

    getComments(photoId: number) {
        return this.http.get<PhotoComment[]>(
            `${API}/api/comment/${photoId}`
        );
    }

    addComment(photoId: number, userId: number, comment: NewComment) {

        let body = JSON.stringify(comment);

        return this.http.post(
            `${API}/api/comment/${photoId}/${userId}`,
            body,
            httpOptions
        );
    }

    removePhoto(photoId: number) {

        return this.http.delete(`${API}/api/photo/${photoId}`);
    }

    like(photoId: number, like: Like) {

        let body = JSON.stringify(like)

        return this.http.post<ResponseApi>(
            `${API}/api/photo/${photoId}/like`, body, httpOptions
        );
    }

}

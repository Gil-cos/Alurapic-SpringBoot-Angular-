import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { NewUser } from 'src/app/core/model/NewUser';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class SignUpService {

    constructor(private http: HttpClient) { }

    checkUserNameTaken(userName: string) {

        return this.http.get(`http://localhost:8080/api/user/exists/${userName}`);
    }

    signup(newUser: NewUser) {
        let body = JSON.stringify(newUser);
        return this.http.post(`http://localhost:8080/api/user`, body, httpOptions);
    }
}
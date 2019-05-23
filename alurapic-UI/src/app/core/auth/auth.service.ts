import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserAuthenticated } from 'src/app/core/model/UserAuthenticated';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private http: HttpClient
  ) { }

  authenticate(user: UserAuthenticated) {

    return this.http
      .post(
        'http://localhost:8080/api/auth',
        user
      )
  }
}
import {Injectable} from '@angular/core';
import {UserLoginDTO} from "../models/userLoginDTO";
import {UserLoginViewDTO} from "../models/userLoginViewDTO";
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private baseUrl = "http://localhost:8080/user"

  constructor(private http: HttpClient) {
  }

  login(userLoginDTO: UserLoginDTO) {
    return this.http.post<UserLoginViewDTO>(this.baseUrl + '/login', userLoginDTO);
  }

  logout() {
    const token: string = this.getAccessToken() ?? "";

    return this.http.post(this.baseUrl + '/logout', null, {
      headers: new HttpHeaders({'Authorization': 'Bearer ' + token}),
      responseType: 'text'
    });
  }

  isLoggedIn() {
    return !!this.getAccessToken() && !this.isTokenExpired();
  }

  isTokenExpired() {
    const token: string = this.getAccessToken() ?? "";

    if (!token) {
      return false;
    }

    const tokenSplit: string = token.split('.')[1];
    const decodedString: string = atob(tokenSplit);
    const jsonString = JSON.parse(decodedString);
    const expiry = (jsonString).exp;

    return (Math.floor((new Date).getTime() / 1000)) >= expiry;
  }

  addAccessToken(accessToken: string) {
    localStorage.setItem('accessToken', accessToken);
  }

  addFullName(fullName: string) {
    localStorage.setItem('fullName', fullName);
  }

  getAccessToken() {
    return localStorage.getItem('accessToken');
  }

  getFullName() {
    return localStorage.getItem('fullName');
  }

  removeAllSavedData() {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('fullName');
  }
}

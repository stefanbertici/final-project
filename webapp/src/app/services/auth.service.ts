import {Injectable} from '@angular/core';
import {UserLoginDto} from "../models/userLoginDto";
import {UserLoginViewDto} from "../models/userLoginViewDto";
import {HttpClient} from "@angular/common/http";
import {JwtHelperService} from "@auth0/angular-jwt";
import {API_URL} from "../utils/constants";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) {
  }

  login(userLoginDto: UserLoginDto) {
    return this.http.post<UserLoginViewDto>(`${API_URL}/users/login`, userLoginDto);
  }

  logout() {
    return this.http.post(`${API_URL}/users/logout`, null, {responseType: 'text'});
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

  getUserId() {
    const helper = new JwtHelperService();
    let decodedAccessToken;

    try {
      decodedAccessToken = helper.decodeToken(this.getAccessToken() ?? '');

      return decodedAccessToken.id;
    } catch (err) {
      return null;
    }
  }

  getUserRole() {
    const helper = new JwtHelperService();
    let decodedAccessToken;

    try {
      decodedAccessToken = helper.decodeToken(this.getAccessToken() ?? '');

      return decodedAccessToken.role;
    } catch (err) {
      return null;
    }
  }


  removeAllSavedData() {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('fullName');
  }
}

import {Injectable} from '@angular/core';
import {UserLoginDTO} from "../models/userLoginDTO";
import {UserLoginViewDTO} from "../models/userLoginViewDTO";
import {HttpClient} from "@angular/common/http";
import {JwtHelperService} from "@auth0/angular-jwt";
import {devOnlyGuardedExpression} from "@angular/compiler";

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
    return this.http.post(this.baseUrl + '/logout', null, {responseType: 'text'});
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

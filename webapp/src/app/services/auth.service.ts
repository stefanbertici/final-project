import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor() {
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

import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {UserLoginDTO} from "../models/userLoginDTO";
import {UserDTO} from "../models/userDTO";
import {UserViewDTO} from "../models/userViewDTO";
import {UserLoginViewDTO} from "../models/userLoginViewDTO";
import {AuthService} from "./auth.service";

@Injectable({
  providedIn: 'root'
})
export class SignupService {

  private baseUrl = "http://localhost:8080/user"

  constructor(private http: HttpClient, private authService: AuthService) {
  }

  signup(userDTO: UserDTO) {
    return this.http.post<UserViewDTO>(this.baseUrl + '/register', userDTO);
  }

  login(userLoginDTO: UserLoginDTO) {
    return this.http.post<UserLoginViewDTO>(this.baseUrl + '/login', userLoginDTO);
  }

  logout() {
    const token: string = this.authService.getAccessToken() ?? "";

    return this.http.post(this.baseUrl + '/logout', null, {
      headers: new HttpHeaders({'Authorization': 'Bearer ' + token}),
      responseType: 'text'
    });
  }
}

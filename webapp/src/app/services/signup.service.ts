import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {UserDto} from "../models/userDto";
import {UserViewDto} from "../models/userViewDto";

@Injectable({
  providedIn: 'root'
})
export class SignupService {

  private baseUrl = "http://localhost:8080/user"

  constructor(private http: HttpClient) {
  }

  signup(userDto: UserDto) {
    return this.http.post<UserViewDto>(this.baseUrl + '/register', userDto);
  }
}

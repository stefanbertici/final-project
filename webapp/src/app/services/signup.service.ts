import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {UserDto} from "../models/userDto";
import {UserViewDto} from "../models/userViewDto";
import {API_URL} from "../utils/constants";

@Injectable({
  providedIn: 'root'
})
export class SignupService {

  constructor(private http: HttpClient) {
  }

  signup(userDto: UserDto) {
    return this.http.post<UserViewDto>(`${API_URL}/users/register`, userDto);
  }
}

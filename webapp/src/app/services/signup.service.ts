import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {UserDTO} from "../models/userDTO";
import {UserViewDTO} from "../models/userViewDTO";

@Injectable({
  providedIn: 'root'
})
export class SignupService {

  private baseUrl = "http://localhost:8080/user"

  constructor(private http: HttpClient) {
  }

  signup(userDTO: UserDTO) {
    return this.http.post<UserViewDTO>(this.baseUrl + '/register', userDTO);
  }
}

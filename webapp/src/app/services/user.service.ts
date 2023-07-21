import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {UserViewDto} from "../models/userViewDto";
import {API_URL} from "../utils/constants";
import {UserDto} from "../models/userDto";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpClient: HttpClient) {
  }

  getAll() {
    return this.httpClient.get<UserViewDto[]>(`${API_URL}/users`);
  }

  getById(id: number) {
    return this.httpClient.get<UserViewDto>(`${API_URL}/users/${id}`);
  }

  update(id: number, userDto: UserDto) {
    return this.httpClient.put<UserViewDto>(`${API_URL}/users/${id}`, userDto);
  }
}

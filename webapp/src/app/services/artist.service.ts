import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {API_URL} from "../utils/constants";
import {ArtistViewDto} from "../models/artistViewDto";
import {ArtistDto} from "../models/artistDto";

@Injectable({
  providedIn: 'root'
})
export class ArtistService {

  constructor(private httpClient: HttpClient) {
  }

  getAll() {
    return this.httpClient.get<ArtistViewDto[]>(`${API_URL}/artists`);
  }

  getById(id: number) {
    return this.httpClient.get<ArtistDto>(`${API_URL}/artists/${id}`);
  }

  create(artistDto: ArtistDto) {
    return this.httpClient.post<ArtistDto>(`${API_URL}/artists`, artistDto);
  }

  update(id: number, artistDto: ArtistDto) {
    return this.httpClient.put<ArtistDto>(`${API_URL}/artists/${id}`, artistDto);
  }

  delete(id: number) {
    return this.httpClient.delete(`${API_URL}/artists/${id}`);
  }
}

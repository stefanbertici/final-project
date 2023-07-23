import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {API_URL} from "../utils/constants";
import {SongViewDto} from "../models/songViewDto";
import {SongDto} from "../models/songDto";

@Injectable({
  providedIn: 'root'
})
export class SongService {

  constructor(private httpClient: HttpClient) {
  }

  getAll() {
    return this.httpClient.get<SongViewDto[]>(`${API_URL}/songs`);
  }

  getById(id: number) {
    return this.httpClient.get<SongViewDto>(`${API_URL}/songs/${id}`);
  }

  create(songDto: SongDto) {
    return this.httpClient.post<SongViewDto>(`${API_URL}/songs`, songDto);
  }

  update(id: number, songDto: SongDto) {
    return this.httpClient.put<SongViewDto>(`${API_URL}/songs/${id}`, songDto);
  }
}

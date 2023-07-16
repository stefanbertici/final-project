import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {API_URL} from "../utils/constants";
import {PlaylistDto} from "../models/playlistDto";
import {PlaylistViewDto} from "../models/playlistViewDto";

@Injectable({
  providedIn: 'root'
})
export class PlaylistService {

  constructor(private httpClient: HttpClient) {
  }

  getAll() {
    return this.httpClient.get<PlaylistDto[]>(`${API_URL}/playlist/`);
  }

  getById(id: number) {
    return this.httpClient.get<PlaylistViewDto>(`${API_URL}/playlist/${id}`);
  }

  add(playlistDto: PlaylistDto) {
    return this.httpClient.post<PlaylistDto>(`${API_URL}/playlist/`, playlistDto);
  }

  update(id: number, playlistDto: PlaylistDto) {
    return this.httpClient.put<PlaylistDto>(`${API_URL}/playlist/${id}`, playlistDto);
  }

  delete(id: number) {
    return this.httpClient.delete(`${API_URL}/playlist/${id}`);
  }

  unfollow(id: number) {
    return this.httpClient.post<PlaylistDto>(`${API_URL}/playlist/${id}/unfollow`, null);
  }

  follow(id: number) {
    return this.httpClient.post<PlaylistDto>(`${API_URL}/playlist/${id}/follow`, null);
  }

  addSong(playlistId: number, songId: number) {
    return this.httpClient.post<PlaylistViewDto>(`${API_URL}/playlist/${playlistId}/add/song/${songId}`, null);
  }

  removeSong(playlistId: number, songId: number) {
    return this.httpClient.post<PlaylistViewDto>(`${API_URL}/playlist/${playlistId}/remove/song/${songId}`, null);
  }
}

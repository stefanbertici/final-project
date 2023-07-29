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
    return this.httpClient.get<PlaylistDto[]>(`${API_URL}/playlists`);
  }

  getById(id: number) {
    return this.httpClient.get<PlaylistViewDto>(`${API_URL}/playlists/${id}`);
  }

  add(playlistDto: PlaylistDto) {
    return this.httpClient.post<PlaylistDto>(`${API_URL}/playlists`, playlistDto);
  }

  update(id: number, playlistDto: PlaylistDto) {
    return this.httpClient.put<PlaylistDto>(`${API_URL}/playlists/${id}`, playlistDto);
  }

  delete(id: number) {
    return this.httpClient.delete(`${API_URL}/playlists/${id}`);
  }

  unfollow(id: number) {
    return this.httpClient.post<PlaylistDto>(`${API_URL}/playlists/${id}/unfollow`, null);
  }

  follow(id: number) {
    return this.httpClient.post<PlaylistDto>(`${API_URL}/playlists/${id}/follow`, null);
  }

  addSong(playlistId: number, songId: number) {
    return this.httpClient.post<PlaylistViewDto>(`${API_URL}/playlists/${playlistId}/add-song/${songId}`, null);
  }

  removeSong(playlistId: number, songId: number) {
    return this.httpClient.post<PlaylistViewDto>(`${API_URL}/playlists/${playlistId}/remove-song/${songId}`, null);
  }
}

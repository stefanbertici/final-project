import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
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
    return this.httpClient.get<PlaylistViewDto[]>(`${API_URL}/playlists`);
  }

  getById(id: number) {
    return this.httpClient.get<PlaylistViewDto>(`${API_URL}/playlists/${id}`);
  }

  add(playlistDto: PlaylistDto) {
    return this.httpClient.post<PlaylistViewDto>(`${API_URL}/playlists`, playlistDto);
  }

  update(id: number, playlistDto: PlaylistDto) {
    return this.httpClient.put<PlaylistViewDto>(`${API_URL}/playlists/${id}`, playlistDto);
  }

  delete(id: number) {
    return this.httpClient.delete(`${API_URL}/playlists/${id}`);
  }

  unfollow(id: number) {
    return this.httpClient.post<PlaylistViewDto>(`${API_URL}/playlists/${id}/unfollow`, null);
  }

  follow(id: number) {
    return this.httpClient.post<PlaylistViewDto>(`${API_URL}/playlists/${id}/follow`, null);
  }

  addSong(playlistId: number, songId: number) {
    return this.httpClient.post<PlaylistViewDto>(`${API_URL}/playlists/${playlistId}/add-song/${songId}`, null);
  }

  removeSong(playlistId: number, songId: number) {
    return this.httpClient.post<PlaylistViewDto>(`${API_URL}/playlists/${playlistId}/remove-song/${songId}`, null);
  }

  addAlbum(playlistId: number, albumId: number) {
    return this.httpClient.post<PlaylistViewDto>(`${API_URL}/playlists/${playlistId}/add-album/${albumId}`, null);
  }

  changeOrder(playlistId: number, songId: number, oldPosition: number, newPosition: number) {
    const params = new HttpParams()
      .set('songId', songId)
      .set('oldPosition', oldPosition)
      .set('newPosition', newPosition);
    const searchParams = params.toString();

    return this.httpClient.post<PlaylistViewDto>(`${API_URL}/playlists/${playlistId}/change-order?${searchParams}`, null);
  }
}

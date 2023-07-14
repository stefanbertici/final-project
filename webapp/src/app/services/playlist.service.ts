import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {API_URL} from "../utils/constants";
import {PlaylistViewDto} from "../models/playlistViewDto";

@Injectable({
  providedIn: 'root'
})
export class PlaylistService {

  constructor(private httpClient: HttpClient) {
  }

  getPlaylists() {
    return this.httpClient.get<PlaylistViewDto[]>(`${API_URL}/playlist/`);
  }

  addPlaylist(playlistViewDto: PlaylistViewDto) {
    return this.httpClient.post<PlaylistViewDto>(`${API_URL}/playlist/`, playlistViewDto);
  }
}

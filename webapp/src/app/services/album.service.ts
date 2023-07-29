import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {API_URL} from "../utils/constants";
import {AlbumViewDto} from "../models/albumViewDto";
import {AlbumDetailViewDto} from "../models/albumDetailViewDto";
import {AlbumDto} from "../models/albumDto";

@Injectable({
  providedIn: 'root'
})
export class AlbumService {

  constructor(private httpClient: HttpClient) {
  }

  getAll() {
    return this.httpClient.get<AlbumViewDto[]>(`${API_URL}/albums`);
  }

  getById(id: number) {
    return this.httpClient.get<AlbumDetailViewDto>(`${API_URL}/albums/${id}`);
  }

  getAllByArtistId(id: number) {
    return this.httpClient.get<AlbumViewDto[]>(`${API_URL}/albums/artist/${id}`);
  }

  create(albumDto: AlbumDto) {
    return this.httpClient.post<AlbumDto>(`${API_URL}/albums`, albumDto);
  }

  update(id: number, albumDto: AlbumDto) {
    return this.httpClient.put<AlbumDto>(`${API_URL}/albums/${id}`, albumDto);
  }

  addSong(albumId: number, songId: number) {
    return this.httpClient.post<AlbumDetailViewDto>(`${API_URL}/albums/${albumId}/add-song/${songId}`, null);
  }

  removeSong(albumId: number, songId: number) {
    return this.httpClient.post<AlbumDetailViewDto>(`${API_URL}/albums/${albumId}/remove-song/${songId}`, null);
  }
}

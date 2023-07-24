import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {API_URL} from "../utils/constants";
import {AlbumViewDto} from "../models/albumViewDto";
import {AlbumDetailViewDto} from "../models/albumDetailViewDto";

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
}

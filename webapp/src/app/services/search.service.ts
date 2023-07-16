import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {API_URL} from "../utils/constants";
import {SearchViewDto} from "../models/searchViewDto";

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  constructor(private httpClient: HttpClient) {
  }

  search(searchTerm: string) {
    return this.httpClient.get<SearchViewDto>(`${API_URL}/search/${searchTerm}`);
  }
}

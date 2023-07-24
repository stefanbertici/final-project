import {SongViewDto} from "./songViewDto";

export interface AlbumDetailViewDto {
  id: number;
  artist: string;
  title: string;
  genre: string;
  releaseDate: Date;
  label: string;
  songs: SongViewDto[];
}

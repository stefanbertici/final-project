import {SongViewDto} from "./songViewDto";

export interface AlbumDetailViewDto {
  id: number;
  artistId: number;
  artist: string;
  title: string;
  description: string;
  genre: string;
  releaseDate: Date;
  label: string;
  songs: SongViewDto[];
}

export interface AlbumDto {
  id: number;
  artistId: number;
  bandId: number;
  title: string;
  description: string;
  genre: string;
  releaseDate: Date;
  label: string;
  songIds: number[];
}

import {SongViewDto} from "./songViewDto";

export interface PlaylistViewDto {
  id: number,
  name: string,
  type: string,
  createdDate: Date,
  updatedDate: Date,
  ownerUserName: string,
  songs: SongViewDto[]
}

import {SongViewDto} from "./songViewDto";

export interface PlaylistViewDto {
  id: number,
  name: string,
  type: string,
  createdDate: Date,
  updatedDate: Date,
  ownerUserId: number,
  ownerUserName: string,
  followableByUser: Boolean,
  unfollowableByUser: Boolean,
  songs: SongViewDto[]
}

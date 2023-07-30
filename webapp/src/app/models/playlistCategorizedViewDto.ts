import {PlaylistViewDto} from "./playlistViewDto";

export interface PlaylistCategorizedViewDto {
  ownedPlaylists: PlaylistViewDto[];
  followedPlaylists: PlaylistViewDto[];
}

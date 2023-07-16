import {PlaylistDto} from "./playlistDto";
import {SongViewDto} from "./songViewDto";

export interface SearchViewDto {
  playlists: PlaylistDto[];
  songs: SongViewDto[];
}

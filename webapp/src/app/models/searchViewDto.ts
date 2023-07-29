import {SongViewDto} from "./songViewDto";
import {AlbumViewDto} from "./albumViewDto";
import {PlaylistViewDto} from "./playlistViewDto";

export interface SearchViewDto {
  playlists: PlaylistViewDto[];
  songs: SongViewDto[];
  albums: AlbumViewDto[];
}

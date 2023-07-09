package ro.ubb.postuniv.musify.mapper;

import ro.ubb.postuniv.musify.dto.PlaylistDto;
import ro.ubb.postuniv.musify.dto.PlaylistViewDto;
import ro.ubb.postuniv.musify.dto.SongViewDto;
import ro.ubb.postuniv.musify.model.Playlist;
import ro.ubb.postuniv.musify.model.Song;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlaylistMapper {

    @Mapping(target = "ownerUserId", expression = "java(playlist.getOwnerUserId())")
    PlaylistDto toDto(Playlist playlist);

    List<PlaylistDto> toDtos(List<Playlist> playlists);

    @Mapping(target = "ownerUserId", expression = "java(playlist.getOwnerUserId())")
    @Mapping(target = "songs", expression = "java(getSongViewDtos(playlist.getSongsInPlaylist()))")
    PlaylistViewDto toViewDto(Playlist playlist);

    Playlist toEntity(PlaylistDto playlistDto);

    default List<SongViewDto> getSongViewDtos(List<Song> songs) {
        SongMapper songMapper = new SongMapperImpl();
        return songMapper.toViewDtos(songs);
    }
}

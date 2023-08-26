package ro.ubb.postuniv.musify.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ro.ubb.postuniv.musify.dto.PlaylistDto;
import ro.ubb.postuniv.musify.dto.PlaylistViewDto;
import ro.ubb.postuniv.musify.dto.SongViewDto;
import ro.ubb.postuniv.musify.model.Playlist;
import ro.ubb.postuniv.musify.model.Song;

@Mapper(componentModel = "spring")
public interface PlaylistMapper {

    @Mapping(target = "ownerUserId", expression = "java(playlist.getOwnerUserId())")
    PlaylistDto toDto(Playlist playlist);

    List<PlaylistDto> toDtos(List<Playlist> playlists);

    @Mapping(target = "ownerUserName", expression = "java(playlist.getOwnerUser().getFullName())")
    @Mapping(target = "songs", expression = "java(getSongViewDtos(playlist.getSongsInPlaylist()))")
    PlaylistViewDto toViewDto(Playlist playlist);

    @Mapping(target = "ownerUserName", expression = "java(playlist.getOwnerUser().getFullName())")
    @Mapping(target = "songs", expression = "java(getSongViewDtos(playlist.getSongsInPlaylist()))")
    PlaylistViewDto toViewDto(Playlist playlist, Boolean followableByUser, Boolean unfollowableByUser);

    Playlist toEntity(PlaylistDto playlistDto);

    default List<SongViewDto> getSongViewDtos(List<Song> songs) {
        SongMapper songMapper = new SongMapperImpl();
        return songMapper.toViewDtos(songs);
    }
}

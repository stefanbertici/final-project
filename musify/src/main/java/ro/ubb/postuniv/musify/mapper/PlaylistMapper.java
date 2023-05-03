package ro.ubb.postuniv.musify.mapper;

import ro.ubb.postuniv.musify.dto.PlaylistDTO;
import ro.ubb.postuniv.musify.dto.PlaylistViewDTO;
import ro.ubb.postuniv.musify.dto.SongViewDTO;
import ro.ubb.postuniv.musify.model.Playlist;
import ro.ubb.postuniv.musify.model.Song;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlaylistMapper {

    @Mapping(target = "ownerUserId", expression = "java(playlist.getOwnerUserId())")
    PlaylistDTO toDto(Playlist playlist);

    List<PlaylistDTO> toDtos(List<Playlist> playlists);

    @Mapping(target = "ownerUserId", expression = "java(playlist.getOwnerUserId())")
    @Mapping(target = "songs", expression = "java(getSongViewDTOS(playlist.getSongsInPlaylist()))")
    PlaylistViewDTO toViewDto(Playlist playlist);

    Playlist toEntity(PlaylistDTO playlistTO);

    default List<SongViewDTO> getSongViewDTOS(List<Song> songs) {
        SongMapper songMapper = new SongMapperImpl();
        return songMapper.toViewDtos(songs);
    }
}

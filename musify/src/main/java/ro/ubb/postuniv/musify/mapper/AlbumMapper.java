package ro.ubb.postuniv.musify.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ro.ubb.postuniv.musify.dto.AlbumDetailViewDto;
import ro.ubb.postuniv.musify.dto.AlbumDto;
import ro.ubb.postuniv.musify.dto.AlbumViewDto;
import ro.ubb.postuniv.musify.dto.SongViewDto;
import ro.ubb.postuniv.musify.model.Album;
import ro.ubb.postuniv.musify.model.Song;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AlbumMapper {

    @Mapping(target = "songIds", expression = "java(album.getSongIds())")
    @Mapping(target = "artistId", expression = "java(album.getArtistId())")
    @Mapping(target = "bandId", expression = "java(album.getBandId())")
    AlbumDto toDto(Album album);

    List<AlbumDto> toDtos(List<Album> albums);

    @Mapping(target = "artist", expression = "java(album.getOverallArtistName())")
    @Mapping(target = "artistId", expression = "java(album.getOverallArtistId())")
    @Mapping(target = "songs", expression = "java(toSongViewDtos(album.getSongs()))")
    AlbumDetailViewDto toDetailViewDto(Album album);

    List<AlbumDetailViewDto> toDetailViewDtos(List<Album> albums);

    @Mapping(target = "artist", expression = "java(album.getOverallArtistName())")
    AlbumViewDto toViewDto(Album album);

    List<AlbumViewDto> toViewDtos(List<Album> albums);

    Album toEntity(AlbumDto albumDto);

    default List<SongViewDto> toSongViewDtos(List<Song> songs) {
        SongMapper songMapper = new SongMapperImpl();

        return songs.stream()
                .map(songMapper::toViewDto)
                .toList();
    }
}

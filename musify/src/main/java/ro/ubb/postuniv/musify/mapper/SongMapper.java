package ro.ubb.postuniv.musify.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ro.ubb.postuniv.musify.dto.SongDto;
import ro.ubb.postuniv.musify.dto.SongViewDto;
import ro.ubb.postuniv.musify.model.Song;

@Mapper(componentModel = "spring")
public interface SongMapper {

    @Mapping(target = "composersIds", expression = "java(song.getComposersIdsList())")
    @Mapping(target = "alternativeTitles", expression = "java(song.getAlternativeSongTitlesList())")
    SongDto toDto(Song song);

    @Mapping(target = "artist", expression = "java(song.getArtistName())")
    @Mapping(target = "alternativeTitles", expression = "java(song.getAlternativeSongTitlesList())")
    @Mapping(target = "albums", expression = "java(song.getAlbumsTitlesList())")
    @Mapping(target = "composers", expression = "java(song.getComposersStageNamesList())")
    SongViewDto toViewDto(Song song);

    List<SongViewDto> toViewDtos(List<Song> songs);

    Song toEntity(SongDto songDto);
}

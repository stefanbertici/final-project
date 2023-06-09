package ro.ubb.postuniv.musify.mapper;

import ro.ubb.postuniv.musify.dto.SongDto;
import ro.ubb.postuniv.musify.dto.SongViewDto;
import ro.ubb.postuniv.musify.model.Song;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SongMapper {

    @Mapping(target = "composersIds", expression = "java(song.getComposersIdsList())")
    @Mapping(target = "alternativeTitles", expression = "java(song.getAlternativeSongTitlesList())")
    SongDto toDto(Song song);

    @Mapping(target = "alternativeTitles", expression = "java(song.getAlternativeSongTitlesList())")
    @Mapping(target = "albums", expression = "java(song.getAlbumsTitlesList())")
    @Mapping(target = "composers", expression = "java(song.getComposersStageNamesList())")
    SongViewDto toViewDto(Song song);

    List<SongViewDto> toViewDtos(List<Song> songs);

    Song toEntity(SongDto songDto);
}

package ro.ubb.postuniv.musify.mapper;

import ro.ubb.postuniv.musify.dto.SongDTO;
import ro.ubb.postuniv.musify.dto.SongViewDTO;
import ro.ubb.postuniv.musify.model.Song;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SongMapper {

    @Mapping(target = "composersIds", expression = "java(song.getComposersIdsList())")
    @Mapping(target = "alternativeTitles", expression = "java(song.getAlternativeSongTitlesList())")
    SongDTO toDto(Song song);

    @Mapping(target = "alternativeTitles", expression = "java(song.getAlternativeSongTitlesList())")
    @Mapping(target = "albums", expression = "java(song.getAlbumsTitlesList())")
    @Mapping(target = "composers", expression = "java(song.getComposersStageNamesList())")
    SongViewDTO toViewDto(Song song);

    List<SongViewDTO> toViewDtos(List<Song> songs);

    Song toEntity(SongDTO songDTO);
}

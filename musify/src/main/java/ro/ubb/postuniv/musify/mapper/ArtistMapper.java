package ro.ubb.postuniv.musify.mapper;

import ro.ubb.postuniv.musify.dto.ArtistDto;
import ro.ubb.postuniv.musify.model.Artist;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ArtistMapper {

    ArtistDto toDto(Artist artist);

    List<ArtistDto> toDtos(List<Artist> artists);

    Artist toEntity(ArtistDto artistDto);
}

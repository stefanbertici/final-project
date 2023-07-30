package ro.ubb.postuniv.musify.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import ro.ubb.postuniv.musify.dto.ArtistDto;
import ro.ubb.postuniv.musify.dto.ArtistViewDto;
import ro.ubb.postuniv.musify.model.Artist;

@Mapper(componentModel = "spring")
public interface ArtistMapper {

    ArtistDto toDto(Artist artist);

    List<ArtistDto> toDtos(List<Artist> artists);

    ArtistViewDto toViewDto(Artist artist);

    List<ArtistViewDto> toViewDtos(List<Artist> artists);

    Artist toEntity(ArtistDto artistDto);
}

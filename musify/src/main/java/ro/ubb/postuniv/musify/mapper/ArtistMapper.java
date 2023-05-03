package ro.ubb.postuniv.musify.mapper;

import ro.ubb.postuniv.musify.dto.ArtistDTO;
import ro.ubb.postuniv.musify.model.Artist;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ArtistMapper {

    ArtistDTO toDto(Artist artist);

    List<ArtistDTO> toDtos(List<Artist> artists);

    Artist toEntity(ArtistDTO artistDTO);
}

package ro.ubb.postuniv.musify.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ro.ubb.postuniv.musify.dto.BandDto;
import ro.ubb.postuniv.musify.model.Band;

@Mapper(componentModel = "spring")
public interface BandMapper {

    @Mapping(target = "bandMembersIds", expression = "java(band.getArtistsIds())")
    BandDto toDto(Band band);

    List<BandDto> toDtos (List<Band> bands);

    Band toEntity(BandDto bandDto);
}

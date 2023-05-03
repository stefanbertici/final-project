package ro.ubb.postuniv.musify.mapper;

import ro.ubb.postuniv.musify.dto.BandDTO;
import ro.ubb.postuniv.musify.model.Band;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BandMapper {

    @Mapping(target = "bandMembersIds", expression = "java(band.getArtistsIds())")
    BandDTO toDto(Band band);

    List<BandDTO> toDtos (List<Band> bands);

    Band toEntity(BandDTO bandDTO);
}

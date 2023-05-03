package ro.ubb.postuniv.musify.service;

import ro.ubb.postuniv.musify.dto.AlbumDTO;
import ro.ubb.postuniv.musify.dto.BandDTO;
import ro.ubb.postuniv.musify.mapper.AlbumMapper;
import ro.ubb.postuniv.musify.mapper.BandMapper;
import ro.ubb.postuniv.musify.model.Artist;
import ro.ubb.postuniv.musify.model.Band;
import ro.ubb.postuniv.musify.repository.ArtistRepository;
import ro.ubb.postuniv.musify.repository.BandRepository;
import ro.ubb.postuniv.musify.utils.RepositoryChecker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class BandService {
    private final RepositoryChecker repositoryChecker;
    private final BandRepository bandRepository;
    private final ArtistRepository artistRepository;
    private final BandMapper bandMapper;
    private final AlbumMapper albumMapper;

    @Transactional
    public List<AlbumDTO> readAlbumsByBandId(Integer id) {
        Band band = repositoryChecker.getBandIfExists(id);

        return albumMapper.toDtos(new ArrayList<>(band.getBandAlbums()));
    }

    @Transactional
    public BandDTO createBand(BandDTO bandDTO) {
        Band band = bandMapper.toEntity(bandDTO);
        band = bandRepository.save(band);

        if (!bandDTO.getBandMembersIds().isEmpty()) {
            addMembersById(band, bandDTO);
        }

        return bandMapper.toDto(band);
    }

    @Transactional
    public BandDTO updateBand(Integer id, BandDTO bandDTO) {
        Band band = repositoryChecker.getBandIfExists(id);

        band.setBandName(bandDTO.getBandName());
        band.setLocation(bandDTO.getLocation());
        band.setActivityStartDate(bandDTO.getActivityStartDate());
        band.setActivityEndDate(bandDTO.getActivityEndDate());

        if (!bandDTO.getBandMembersIds().isEmpty()) {
            clearMembers(band);
            addMembersById(band, bandDTO);
        }

        return bandMapper.toDto(band);
    }

    public void clearMembers(Band band) {
        Set<Artist> currentMembers = band.getArtists();
        for (Artist artist : currentMembers) {
            artist.getBands().remove(band);
        }

        band.getArtists().clear();
    }

    public void addMembersById(Band band, BandDTO bandDTO) {
        List<Artist> artists = (List<Artist>) artistRepository.findAllById(bandDTO.getBandMembersIds());
        for (Artist artist : artists) {
            band.addArtist(artist);
        }
    }
}

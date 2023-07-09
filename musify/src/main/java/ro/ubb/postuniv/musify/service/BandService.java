package ro.ubb.postuniv.musify.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.postuniv.musify.dto.AlbumDto;
import ro.ubb.postuniv.musify.dto.BandDto;
import ro.ubb.postuniv.musify.mapper.AlbumMapper;
import ro.ubb.postuniv.musify.mapper.BandMapper;
import ro.ubb.postuniv.musify.model.Artist;
import ro.ubb.postuniv.musify.model.Band;
import ro.ubb.postuniv.musify.repository.ArtistRepository;
import ro.ubb.postuniv.musify.repository.BandRepository;
import ro.ubb.postuniv.musify.utils.RepositoryChecker;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BandService {

    private final RepositoryChecker repositoryChecker;
    private final BandRepository bandRepository;
    private final ArtistRepository artistRepository;
    private final BandMapper bandMapper;
    private final AlbumMapper albumMapper;

    @Transactional
    public List<AlbumDto> readAlbumsByBandId(Integer id) {
        Band band = repositoryChecker.getBandIfExists(id);

        return albumMapper.toDtos(new ArrayList<>(band.getBandAlbums()));
    }

    @Transactional
    public BandDto createBand(BandDto bandDto) {
        Band band = bandMapper.toEntity(bandDto);
        band = bandRepository.save(band);

        if (!bandDto.getBandMembersIds().isEmpty()) {
            addMembersById(band, bandDto);
        }

        return bandMapper.toDto(band);
    }

    @Transactional
    public BandDto updateBand(Integer id, BandDto bandDto) {
        Band band = repositoryChecker.getBandIfExists(id);

        band.setBandName(bandDto.getBandName());
        band.setLocation(bandDto.getLocation());
        band.setActivityStartDate(bandDto.getActivityStartDate());
        band.setActivityEndDate(bandDto.getActivityEndDate());

        if (!bandDto.getBandMembersIds().isEmpty()) {
            clearMembers(band);
            addMembersById(band, bandDto);
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

    public void addMembersById(Band band, BandDto bandDto) {
        List<Artist> artists = (List<Artist>) artistRepository.findAllById(bandDto.getBandMembersIds());
        for (Artist artist : artists) {
            band.addArtist(artist);
        }
    }
}

package ro.ubb.postuniv.musify.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.postuniv.musify.dto.AlbumDto;
import ro.ubb.postuniv.musify.dto.ArtistDto;
import ro.ubb.postuniv.musify.dto.ArtistViewDto;
import ro.ubb.postuniv.musify.mapper.AlbumMapper;
import ro.ubb.postuniv.musify.mapper.ArtistMapper;
import ro.ubb.postuniv.musify.model.Album;
import ro.ubb.postuniv.musify.model.Artist;
import ro.ubb.postuniv.musify.model.Band;
import ro.ubb.postuniv.musify.model.Song;
import ro.ubb.postuniv.musify.repository.AlbumRepository;
import ro.ubb.postuniv.musify.repository.ArtistRepository;
import ro.ubb.postuniv.musify.utils.RepositoryChecker;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ArtistService {

    private final RepositoryChecker repositoryChecker;
    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;
    private final ArtistMapper artistMapper;
    private final AlbumMapper albumMapper;

    @Transactional
    public List<AlbumDto> readAlbumsByArtistId(Integer id) {
        Artist artist = repositoryChecker.getArtistIfExists(id);

        return albumMapper.toDtos(new ArrayList<>(artist.getArtistAlbums()));
    }

    @Transactional
    public List<ArtistViewDto> readAll() {
        List<Artist> artists = new ArrayList<>();
        artistRepository.findAll().forEach(artists::add);

        return artists.stream()
                .map(artistMapper::toViewDto)
                .sorted(Comparator.comparing(ArtistViewDto::getStageName))
                .toList();
    }

    @Transactional
    public ArtistDto readById(Integer id) {
        Artist artist = repositoryChecker.getArtistIfExists(id);

        return artistMapper.toDto(artist);
    }

    @Transactional
    public ArtistDto create(ArtistDto artistDto) {
        if (artistDto.isNotValidActivityDates()) {
            throw new IllegalArgumentException("Activity start and end dates must be the year in numeric format. " +
                    "\n Additionally the end date can be \"present\" if the artist is still active");
        }

        Artist artist = artistMapper.toEntity(artistDto);
        artist = artistRepository.save(artist);

        return artistMapper.toDto(artist);
    }

    @Transactional
    public ArtistDto update(Integer id, ArtistDto artistDto) {
        Artist artist = repositoryChecker.getArtistIfExists(id);

        artist.setFirstName(artistDto.getFirstName());
        artist.setLastName(artistDto.getLastName());
        artist.setStageName(artistDto.getStageName());
        artist.setBirthday(artistDto.getBirthday());
        artist.setActivityStartDate(artistDto.getActivityStartDate());
        artist.setActivityEndDate(artistDto.getActivityEndDate());

        return artistMapper.toDto(artist);
    }

    @Transactional
    public ArtistDto delete(Integer id) {
        Artist artist = repositoryChecker.getArtistIfExists(id);

        Set<Band> bands = artist.getBands();
        Set<Album> albums = artist.getArtistAlbums();

        bands.forEach(band -> band.removeArtist(artist));
        albums.stream()
                .map(Album::getSongs)
                .flatMap(Collection::stream)
                .forEach(Song::removeFromPlaylists);

        albumRepository.deleteAll(albums);
        artistRepository.delete(artist);

        return artistMapper.toDto(artist);
    }
}

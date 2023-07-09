package ro.ubb.postuniv.musify.service;

import lombok.RequiredArgsConstructor;
import ro.ubb.postuniv.musify.dto.AlbumDto;
import ro.ubb.postuniv.musify.dto.ArtistDto;
import ro.ubb.postuniv.musify.mapper.AlbumMapper;
import ro.ubb.postuniv.musify.mapper.ArtistMapper;
import ro.ubb.postuniv.musify.model.Artist;
import ro.ubb.postuniv.musify.repository.ArtistRepository;
import ro.ubb.postuniv.musify.utils.RepositoryChecker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtistService {

    private final RepositoryChecker repositoryChecker;
    private final ArtistRepository artistRepository;
    private final ArtistMapper artistMapper;
    private final AlbumMapper albumMapper;

    @Transactional
    public List<AlbumDto> readAlbumsByArtistId(Integer id) {
        Artist artist = repositoryChecker.getArtistIfExists(id);

        return albumMapper.toDtos(new ArrayList<>(artist.getArtistAlbums()));
    }

    @Transactional
    public ArtistDto createArtist(ArtistDto artistDto) {
        Artist artist = artistMapper.toEntity(artistDto);
        artist = artistRepository.save(artist);

        return artistMapper.toDto(artist);
    }

    @Transactional
    public ArtistDto updateArtist(Integer id, ArtistDto artistDto) {
        Artist artist = repositoryChecker.getArtistIfExists(id);

        artist.setFirstName(artistDto.getFirstName());
        artist.setLastName(artistDto.getLastName());
        artist.setStageName(artistDto.getStageName());
        artist.setBirthday(artistDto.getBirthday());
        artist.setActivityStartDate(artistDto.getActivityStartDate());
        artist.setActivityEndDate(artistDto.getActivityEndDate());

        return artistMapper.toDto(artist);
    }
}

package ro.ubb.postuniv.musify.service;

import ro.ubb.postuniv.musify.dto.SearchViewDTO;
import ro.ubb.postuniv.musify.mapper.AlbumMapper;
import ro.ubb.postuniv.musify.mapper.ArtistMapper;
import ro.ubb.postuniv.musify.mapper.BandMapper;
import ro.ubb.postuniv.musify.mapper.SongMapper;
import ro.ubb.postuniv.musify.repository.AlbumRepository;
import ro.ubb.postuniv.musify.repository.ArtistRepository;
import ro.ubb.postuniv.musify.repository.BandRepository;
import ro.ubb.postuniv.musify.repository.SongRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class SearchService {
    private final AlbumRepository albumRepository;
    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;
    private final BandRepository bandRepository;
    private final ArtistMapper artistMapper;
    private final BandMapper bandMapper;
    private final SongMapper songMapper;
    private final AlbumMapper albumMapper;

    @Transactional
    public SearchViewDTO search(String searchTerm) {
        SearchViewDTO searchViewDTO = new SearchViewDTO();
        searchViewDTO.setArtists(artistMapper.toDtos(artistRepository.findAllByStageNameContainingIgnoreCase(searchTerm)));
        searchViewDTO.setBands(bandMapper.toDtos(bandRepository.findAllByBandNameContainingIgnoreCase(searchTerm)));
        searchViewDTO.setAlbums(albumMapper.toDtos(albumRepository.findAllByTitleContainingIgnoreCase(searchTerm)));
        searchViewDTO.setSongs(songMapper.toViewDtos(songRepository.findAllByTitleAndAlternativeTitles(searchTerm)));

        return searchViewDTO;
    }
}

package ro.ubb.postuniv.musify.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.postuniv.musify.dto.SearchViewDto;
import ro.ubb.postuniv.musify.mapper.*;
import ro.ubb.postuniv.musify.repository.*;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final AlbumRepository albumRepository;
    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;
    private final BandRepository bandRepository;
    private final PlaylistRepository playlistRepository;
    private final ArtistMapper artistMapper;
    private final BandMapper bandMapper;
    private final SongMapper songMapper;
    private final AlbumMapper albumMapper;
    private final PlaylistMapper playlistMapper;

    @Transactional
    public SearchViewDto search(String searchTerm) {
        SearchViewDto searchViewDto = new SearchViewDto();

        searchViewDto.setPlaylists(playlistMapper.toDtos(playlistRepository.findAllByNameContainingIgnoreCase(searchTerm)
                .stream()
                .filter(p -> p.getType().equals("public"))
                .toList()));
        searchViewDto.setArtists(artistMapper.toDtos(artistRepository.findAllByStageNameContainingIgnoreCase(searchTerm)));
        searchViewDto.setBands(bandMapper.toDtos(bandRepository.findAllByBandNameContainingIgnoreCase(searchTerm)));
        searchViewDto.setAlbums(albumMapper.toDtos(albumRepository.findAllByTitleContainingIgnoreCase(searchTerm)));
        searchViewDto.setSongs(songMapper.toViewDtos(songRepository.findAllByTitleAndAlternativeTitles(searchTerm)));

        return searchViewDto;
    }
}

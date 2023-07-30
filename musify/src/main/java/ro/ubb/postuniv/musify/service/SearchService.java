package ro.ubb.postuniv.musify.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.postuniv.musify.dto.SearchViewDto;
import ro.ubb.postuniv.musify.mapper.AlbumMapper;
import ro.ubb.postuniv.musify.mapper.ArtistMapper;
import ro.ubb.postuniv.musify.mapper.BandMapper;
import ro.ubb.postuniv.musify.mapper.PlaylistMapper;
import ro.ubb.postuniv.musify.mapper.SongMapper;
import ro.ubb.postuniv.musify.repository.AlbumRepository;
import ro.ubb.postuniv.musify.repository.ArtistRepository;
import ro.ubb.postuniv.musify.repository.BandRepository;
import ro.ubb.postuniv.musify.repository.PlaylistRepository;
import ro.ubb.postuniv.musify.repository.SongRepository;

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

        searchViewDto.setPlaylists(playlistMapper.toViewDtos(
                playlistRepository.findAllByNameContainingIgnoreCase(searchTerm).stream()
                        .filter(p -> p.getType().equals("public"))
                        .toList()));
        searchViewDto.setSongs(songMapper.toViewDtos(
                songRepository.findAllByTitleAndAlternativeTitles(searchTerm).stream()
                        .filter(song -> !song.getAlbums().isEmpty())
                        .toList()));
        searchViewDto.setArtists(artistMapper.toViewDtos(
                artistRepository.findAllByStageNameContainingIgnoreCase(searchTerm)));
        searchViewDto.setBands(bandMapper.toDtos(
                bandRepository.findAllByBandNameContainingIgnoreCase(searchTerm)));
        searchViewDto.setAlbums(albumMapper.toViewDtos(
                albumRepository.findAllByTitleContainingIgnoreCase(searchTerm)));

        return searchViewDto;
    }
}

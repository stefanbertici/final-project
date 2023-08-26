package ro.ubb.postuniv.musify.service;

import static ro.ubb.postuniv.musify.utils.checkers.PlaylistChecker.*;

import java.util.ArrayList;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.postuniv.musify.dto.SearchViewDto;
import ro.ubb.postuniv.musify.mapper.AlbumMapper;
import ro.ubb.postuniv.musify.mapper.ArtistMapper;
import ro.ubb.postuniv.musify.mapper.BandMapper;
import ro.ubb.postuniv.musify.mapper.PlaylistMapper;
import ro.ubb.postuniv.musify.mapper.SongMapper;
import ro.ubb.postuniv.musify.model.Song;
import ro.ubb.postuniv.musify.model.User;
import ro.ubb.postuniv.musify.repository.AlbumRepository;
import ro.ubb.postuniv.musify.repository.ArtistRepository;
import ro.ubb.postuniv.musify.repository.BandRepository;
import ro.ubb.postuniv.musify.repository.PlaylistRepository;
import ro.ubb.postuniv.musify.repository.SongRepository;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final UserService userService;
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
        User user = userService.readCurrentUser();

        searchViewDto.setPlaylists(playlistRepository.findAllByNameContainingIgnoreCase(searchTerm).stream()
                .filter(p -> p.getType().equals("public"))
                .map(p -> playlistMapper.toViewDto(p, isFollowableByUser(p, user), isUnfollowableByUser(p, user)))
                .toList());

        Set<Song> firstBatchOfSongs = songRepository.findAllByTitleAndAlternativeTitlesContainingIgnoreCase(searchTerm);
        Set<Song> secondBatchOfSongs = songRepository.findAllByAlbumsArtistStageNameContainingIgnoreCase(searchTerm);
        firstBatchOfSongs.addAll(secondBatchOfSongs);
        ArrayList<Song> songs = new ArrayList<>(firstBatchOfSongs);
        searchViewDto.setSongs(songMapper.toViewDtos(songs.stream()
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

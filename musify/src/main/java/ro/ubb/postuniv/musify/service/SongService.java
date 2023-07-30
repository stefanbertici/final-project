package ro.ubb.postuniv.musify.service;

import static java.util.Optional.*;
import static ro.ubb.postuniv.musify.utils.checkers.UserChecker.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.postuniv.musify.dto.SongDto;
import ro.ubb.postuniv.musify.dto.SongViewDto;
import ro.ubb.postuniv.musify.exception.UnauthorizedException;
import ro.ubb.postuniv.musify.mapper.SongMapper;
import ro.ubb.postuniv.musify.model.Album;
import ro.ubb.postuniv.musify.model.AlternativeSongTitle;
import ro.ubb.postuniv.musify.model.Artist;
import ro.ubb.postuniv.musify.model.Playlist;
import ro.ubb.postuniv.musify.model.Song;
import ro.ubb.postuniv.musify.repository.AlternativeSongTitleRepository;
import ro.ubb.postuniv.musify.repository.ArtistRepository;
import ro.ubb.postuniv.musify.repository.SongRepository;
import ro.ubb.postuniv.musify.security.JwtService;
import ro.ubb.postuniv.musify.utils.checkers.RepositoryChecker;

@Service
@RequiredArgsConstructor
public class SongService {

    private final JwtService jwtService;
    private final RepositoryChecker repositoryChecker;
    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;
    private final AlternativeSongTitleRepository alternativeSongTitleRepository;
    private final SongMapper songMapper;

    @Transactional
    public List<SongViewDto> readAll() {
        return songRepository.findAll().stream()
                .map(songMapper::toViewDto)
                .toList();
    }

    @Transactional
    public SongViewDto readById(Integer id) {
        Song song = repositoryChecker.getSongIfExists(id);

        return songMapper.toViewDto(song);
    }

    @Transactional
    public List<SongViewDto> readAllByArtistId(Integer id) {
        Artist artist = repositoryChecker.getArtistIfExists(id);

        return artist.getArtistAlbums().stream()
                .map(Album::getSongs)
                .flatMap(Collection::stream)
                .filter(distinctByKey(Song::getId))
                .map(songMapper::toViewDto)
                .toList();
    }

    @Transactional
    public List<SongViewDto> readAllByAlbumId(Integer id) {
        Album album = repositoryChecker.getAlbumIfExists(id);
        List<Song> songs = album.getSongs();

        return songMapper.toViewDtos(songs);
    }

    @Transactional
    public List<SongViewDto> readAllByPlaylistId(Integer id) {
        Playlist playlist = repositoryChecker.getPlaylistIfExists(id);

        if (playlist.getType().equals("private") && isCurrentUserNotOwnerOfPlaylist(jwtService.getCurrentUserId(), playlist)) {
            throw new UnauthorizedException("You cannot view this private playlist");
        }

        return songMapper.toViewDtos(playlist.getSongsInPlaylist());
    }

    @Transactional
    public SongViewDto create(SongDto songDto) {
        validateAdminRole(jwtService.getCurrentUserRole());

        Song song = songMapper.toEntity(songDto);
        songRepository.save(song);

        ofNullable(songDto.getAlternativeTitles())
                .ifPresent(titles -> {
                    if (!titles.isEmpty()) {
                        addAlternativeTitles(song, songDto);
                    }
                });

        ofNullable(songDto.getComposersIds())
                .ifPresent(composerIds -> {
                    if (!composerIds.isEmpty()) {
                        addComposersById(song, songDto);
                    }
                });

        return songMapper.toViewDto(song);
    }

    @Transactional
    public SongViewDto update(Integer id, SongDto songDto) {
        validateAdminRole(jwtService.getCurrentUserRole());

        Song song = repositoryChecker.getSongIfExists(id);

        song.setTitle(songDto.getTitle());
        song.setDuration(songDto.getDuration());
        song.setCreatedDate(songDto.getCreatedDate());

        ofNullable(songDto.getAlternativeTitles())
                .ifPresent(titles -> {
                    if (!titles.isEmpty()) {
                        clearAlternativeTitles(song);
                        addAlternativeTitles(song, songDto);
                    }
                });

        ofNullable(songDto.getComposersIds())
                .ifPresent(composerIds -> {
                    if (!composerIds.isEmpty()) {
                        clearComposers(song);
                        addComposersById(song, songDto);
                    }
                });

        return songMapper.toViewDto(song);
    }

    private void clearComposers(Song song) {
        for (Artist artist : song.getComposers()) {
            artist.getComposedSongs().remove(song);
        }

        song.getComposers().clear();
    }

    private void addComposersById(Song song, SongDto songDto) {
        List<Artist> artists = (List<Artist>) artistRepository.findAllById(songDto.getComposersIds());
        for (Artist artist : artists) {
            artist.addComposedSong(song);
        }
    }

    private void addAlternativeTitles(Song song, SongDto songDto) {
        for (String title : songDto.getAlternativeTitles()) {
            AlternativeSongTitle newTitle = new AlternativeSongTitle();
            newTitle.setAlternativeTitle(title);
            newTitle = alternativeSongTitleRepository.save(newTitle);
            song.addAlternativeSongTitle(newTitle);
        }
    }

    private void clearAlternativeTitles(Song song) {
        alternativeSongTitleRepository.deleteAll(song.getAlternativeSongTitles());
        song.getAlternativeSongTitles().clear();
    }

    private <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
}

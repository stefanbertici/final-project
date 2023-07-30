package ro.ubb.postuniv.musify.utils.checkers;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.ubb.postuniv.musify.exception.ResourceNotFoundException;
import ro.ubb.postuniv.musify.exception.UnauthorizedException;
import ro.ubb.postuniv.musify.model.Album;
import ro.ubb.postuniv.musify.model.Artist;
import ro.ubb.postuniv.musify.model.Band;
import ro.ubb.postuniv.musify.model.Playlist;
import ro.ubb.postuniv.musify.model.Song;
import ro.ubb.postuniv.musify.model.User;
import ro.ubb.postuniv.musify.repository.AlbumRepository;
import ro.ubb.postuniv.musify.repository.ArtistRepository;
import ro.ubb.postuniv.musify.repository.BandRepository;
import ro.ubb.postuniv.musify.repository.PlaylistRepository;
import ro.ubb.postuniv.musify.repository.SongRepository;
import ro.ubb.postuniv.musify.repository.UserRepository;
import ro.ubb.postuniv.musify.security.JwtService;

@RequiredArgsConstructor
@Component
public class RepositoryChecker {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    private final BandRepository bandRepository;
    private final PlaylistRepository playlistRepository;
    private final SongRepository songRepository;

    public User getUserIfExists(Integer id) {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ResourceNotFoundException("There is no user with id = " + id);
        }

        return optional.get();
    }

    public User getCurrentUser() {
        Optional<User> optional = userRepository.findById(jwtService.getCurrentUserId());
        if (optional.isEmpty()) {
            throw new UnauthorizedException("You need to log in");
        }

        return optional.get();
    }

    public void checkIfEmailIsTaken(Integer id, String email) {
        Optional<User> optional = userRepository.findUserByEmail(email);
        if (optional.isPresent() && !id.equals(optional.get().getId())) {
            throw new IllegalArgumentException("Given e-mail address is already taken");
        }
    }

    public Album getAlbumIfExists(Integer id) {
        Optional<Album> optional = albumRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ResourceNotFoundException("There is no album with id = " + id);
        }

        return optional.get();
    }

    public Artist getArtistIfExists(Integer id) {
        Optional<Artist> optional = artistRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ResourceNotFoundException("There is no artist with id = " + id);
        }

        return optional.get();
    }

    public Band getBandIfExists(Integer id) {
        Optional<Band> optional = bandRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ResourceNotFoundException("There is no band with id = " + id);
        }

        return optional.get();
    }

    public Playlist getPlaylistIfExists(Integer id) {
        Optional<Playlist> optional = playlistRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ResourceNotFoundException("There is no playlist with id = " + id);
        }

        return optional.get();
    }

    public Song getSongIfExists(Integer id) {
        Optional<Song> optional = songRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ResourceNotFoundException("There is no song with id = " + id);
        }

        return optional.get();
    }
}

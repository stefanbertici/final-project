package ro.ubb.postuniv.musify.utils.checkers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.ubb.postuniv.musify.exception.ResourceNotFoundException;
import ro.ubb.postuniv.musify.exception.UnauthorizedException;
import ro.ubb.postuniv.musify.model.*;
import ro.ubb.postuniv.musify.repository.*;
import ro.ubb.postuniv.musify.security.JwtUtils;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class RepositoryChecker {

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
        Optional<User> optional = userRepository.findById(JwtUtils.getCurrentUserId());
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

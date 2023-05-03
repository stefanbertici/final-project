package ro.ubb.postuniv.musify.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.ubb.postuniv.musify.exception.ResourceNotFoundException;
import ro.ubb.postuniv.musify.exception.UnauthorizedException;
import ro.ubb.postuniv.musify.model.*;
import ro.ubb.postuniv.musify.repository.*;
import ro.ubb.postuniv.musify.security.JwtUtils;

import java.util.Optional;

@Component
public class RepositoryChecker {
    private final UserRepository userRepository;
    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    private final BandRepository bandRepository;
    private final PlaylistRepository playlistRepository;
    private final SongRepository songRepository;

    @Autowired
    public RepositoryChecker(UserRepository userRepository, AlbumRepository albumRepository, ArtistRepository artistRepository, BandRepository bandRepository, PlaylistRepository playlistRepository, SongRepository songRepository) {
        this.userRepository = userRepository;
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
        this.bandRepository = bandRepository;
        this.playlistRepository = playlistRepository;
        this.songRepository = songRepository;
    }

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

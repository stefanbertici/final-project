package ro.ubb.postuniv.musify.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.postuniv.musify.dto.PlaylistDto;
import ro.ubb.postuniv.musify.dto.PlaylistViewDto;
import ro.ubb.postuniv.musify.exception.UnauthorizedException;
import ro.ubb.postuniv.musify.mapper.PlaylistMapper;
import ro.ubb.postuniv.musify.model.Album;
import ro.ubb.postuniv.musify.model.Playlist;
import ro.ubb.postuniv.musify.model.Song;
import ro.ubb.postuniv.musify.model.User;
import ro.ubb.postuniv.musify.repository.PlaylistRepository;
import ro.ubb.postuniv.musify.repository.UserRepository;
import ro.ubb.postuniv.musify.security.JwtUtils;
import ro.ubb.postuniv.musify.utils.RepositoryChecker;
import ro.ubb.postuniv.musify.utils.UserChecker;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaylistService {

    private final RepositoryChecker repositoryChecker;
    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;
    private final PlaylistMapper playlistMapper;

    @Transactional
    public List<PlaylistDto> readAll() {
        User user = repositoryChecker.getCurrentUser();

        Set<Playlist> ownedPlaylists = user.getOwnedPlaylists();
        Set<Playlist> followedPlaylists = user.getFollowedPlaylists();

        List<PlaylistDto> result = ownedPlaylists
                .stream()
                .map(playlistMapper::toDto)
                .collect(Collectors.toList());

        followedPlaylists.forEach(playlist -> result.add(playlistMapper.toDto(playlist)));

        return result.stream()
                .sorted(Comparator.comparing(PlaylistDto::getName))
                .toList();
    }

    @Transactional
    public PlaylistViewDto readById(Integer id) {
        Playlist playlist = repositoryChecker.getPlaylistIfExists(id);

        return playlistMapper.toViewDto(playlist);
    }

    @Transactional
    public PlaylistDto create(PlaylistDto playlistDto) {
        if (playlistDto.isNotPrivateOrPublic()) {
            throw new IllegalArgumentException("Playlist type must be \"private\" or \"public\"");
        }

        User user = repositoryChecker.getCurrentUser();

        Playlist playlist = playlistMapper.toEntity(playlistDto);
        playlist.setCreatedDate(LocalDate.now());
        playlist.setUpdatedDate(LocalDate.now());
        playlist = playlistRepository.save(playlist);

        user.addOwnedPlaylist(playlist);

        return playlistMapper.toDto(playlist);
    }

    @Transactional
    public PlaylistDto update(Integer id, PlaylistDto playlistDto) {
        if (playlistDto.isNotPrivateOrPublic()) {
            throw new IllegalArgumentException("Playlist type must be \"private\" or \"public\"");
        }

        Playlist playlist = repositoryChecker.getPlaylistIfExists(id);

        if (UserChecker.isCurrentUserNotOwnerOfPlaylist(playlist)) {
            throw new UnauthorizedException("You can't modify playlists you do not own");
        }

        playlist.setName(playlistDto.getName());
        playlist.setType(playlistDto.getType());
        playlist.setUpdatedDate(LocalDate.now());

        return playlistMapper.toDto(playlist);
    }

    @Transactional
    public PlaylistViewDto addSongToPlaylist(Integer playlistId, Integer songId) {
        Playlist playlist = repositoryChecker.getPlaylistIfExists(playlistId);
        Song song = repositoryChecker.getSongIfExists(songId);

        if (UserChecker.isCurrentUserNotOwnerOfPlaylist(playlist)) {
            throw new UnauthorizedException("You can't modify playlists you do not own");
        }

        if (!playlist.getSongsInPlaylist().contains(song)) {
            playlist.addSong(song);
            playlist.setUpdatedDate(LocalDate.now());
        }

        return playlistMapper.toViewDto(playlist);
    }

    @Transactional
    public PlaylistViewDto removeSongFromPlaylist(Integer playlistId, Integer songId) {
        Playlist playlist = repositoryChecker.getPlaylistIfExists(playlistId);
        Song song = repositoryChecker.getSongIfExists(songId);

        if (UserChecker.isCurrentUserNotOwnerOfPlaylist(playlist)) {
            throw new UnauthorizedException("You can't modify playlists you do not own");
        }

        if (playlist.getSongsInPlaylist().contains(song)) {
            playlist.removeSong(song);
            playlist.setUpdatedDate(LocalDate.now());
        }

        return playlistMapper.toViewDto(playlist);
    }

    @Transactional
    public PlaylistViewDto addAlbumToPlaylist(Integer playlistId, Integer albumId) {
        Playlist playlist = repositoryChecker.getPlaylistIfExists(playlistId);
        Album album = repositoryChecker.getAlbumIfExists(albumId);


        if (UserChecker.isCurrentUserNotOwnerOfPlaylist(playlist)) {
            throw new UnauthorizedException("You can't modify playlists you do not own");
        }

        for (Song song : album.getSongs()) {
            if (!playlist.getSongsInPlaylist().contains(song)) {
                playlist.addSong(song);
            }
        }

        playlist.setUpdatedDate(LocalDate.now());

        return playlistMapper.toViewDto(playlist);
    }

    @Transactional
    public PlaylistViewDto changeSongOrder(Integer playlistId, Integer songId, Integer oldPosition, Integer newPosition) {
        Playlist playlist = repositoryChecker.getPlaylistIfExists(playlistId);
        Song song = repositoryChecker.getSongIfExists(songId);

        if (UserChecker.isCurrentUserNotOwnerOfPlaylist(playlist)) {
            throw new UnauthorizedException("You can't modify playlists you do not own");
        }

        List<Song> songs = playlist.getSongsInPlaylist();
        if (oldPosition < 1 || oldPosition > songs.size() || newPosition < 1 || newPosition > songs.size()) {
            throw new IllegalArgumentException("The given positions are not in range.");
        }

        if (songs.get(oldPosition - 1).getId().intValue() != songId.intValue()) {
            throw new IllegalArgumentException("The song introduced is not in the correct position");
        }

        if (!oldPosition.equals(newPosition)) {
            songs.remove(song);
            songs.add(newPosition - 1, song);
            playlist.setUpdatedDate(LocalDate.now());
            playlistRepository.save(playlist);
        }

        return playlistMapper.toViewDto(playlist);
    }

    @Transactional
    public PlaylistDto delete(Integer id) {
        Playlist playlist = repositoryChecker.getPlaylistIfExists(id);

        User user = userRepository.findById(JwtUtils.getCurrentUserId())
                .orElseThrow(() -> new UnauthorizedException("You need to log in"));

        if (UserChecker.isCurrentUserNotOwnerOfPlaylist(playlist)) {
            throw new UnauthorizedException("You can only delete your own playlists");
        }

        user.removeOwnedPlaylist(playlist);

        Set<User> followers = playlist.getFollowerUsers();
        for (User follower : followers) {
            follower.getFollowedPlaylists().remove(playlist);
        }

        playlistRepository.delete(playlist);

        return playlistMapper.toDto(playlist);
    }

    @Transactional
    public PlaylistDto follow(Integer id) {
        Playlist playlist = repositoryChecker.getPlaylistIfExists(id);

        User user = userRepository.findById(JwtUtils.getCurrentUserId())
                .orElseThrow(() -> new UnauthorizedException("You need to log in"));

        if (playlist.getFollowerUsers().contains(user)) {
            throw new UnauthorizedException("You already follow this playlist");
        }

        if (!playlist.getType().equals("public")) {
            throw new UnauthorizedException("Private playlist cannot be followed");
        }

        if (playlist.getOwnerUserId().intValue() == user.getId().intValue()) {
            throw new UnauthorizedException("You cannot follow your own playlist");
        }

        user.addFollowedPlaylist(playlist);

        return playlistMapper.toDto(playlist);
    }

    @Transactional
    public PlaylistDto unfollow(Integer id) {
        Playlist playlist = repositoryChecker.getPlaylistIfExists(id);

        User user = userRepository.findById(JwtUtils.getCurrentUserId())
                .orElseThrow(() -> new UnauthorizedException("You need to log in"));

        if (!playlist.getFollowerUsers().contains(user)) {
            throw new UnauthorizedException("You have not followed this playlist");
        }

        user.removeFollowedPlaylist(playlist);

        return playlistMapper.toDto(playlist);
    }
}

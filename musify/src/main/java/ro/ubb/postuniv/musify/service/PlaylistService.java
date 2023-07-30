package ro.ubb.postuniv.musify.service;

import static ro.ubb.postuniv.musify.utils.checkers.PositionChecker.*;
import static ro.ubb.postuniv.musify.utils.checkers.UserChecker.*;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.postuniv.musify.dto.PlaylistCategorizedViewDto;
import ro.ubb.postuniv.musify.dto.PlaylistDto;
import ro.ubb.postuniv.musify.dto.PlaylistViewDto;
import ro.ubb.postuniv.musify.exception.UnauthorizedException;
import ro.ubb.postuniv.musify.mapper.PlaylistMapper;
import ro.ubb.postuniv.musify.model.Album;
import ro.ubb.postuniv.musify.model.Playlist;
import ro.ubb.postuniv.musify.model.Song;
import ro.ubb.postuniv.musify.model.User;
import ro.ubb.postuniv.musify.repository.PlaylistRepository;
import ro.ubb.postuniv.musify.security.JwtService;
import ro.ubb.postuniv.musify.utils.checkers.RepositoryChecker;

@Service
@RequiredArgsConstructor
public class PlaylistService {

    private final JwtService jwtService;
    private final RepositoryChecker repositoryChecker;
    private final PlaylistRepository playlistRepository;
    private final PlaylistMapper playlistMapper;

    @Transactional
    public PlaylistCategorizedViewDto readAll() {
        User user = repositoryChecker.getCurrentUser();

        List<PlaylistViewDto> ownedPlaylists = playlistMapper.toViewDtos(
                user.getOwnedPlaylists()
                        .stream()
                        .sorted(Comparator.comparing(Playlist::getName))
                        .toList());
        List<PlaylistViewDto> followedPlaylists = playlistMapper.toViewDtos(
                user.getFollowedPlaylists()
                        .stream()
                        .sorted(Comparator.comparing(Playlist::getName))
                        .toList());

        return new PlaylistCategorizedViewDto(ownedPlaylists, followedPlaylists);
    }

    @Transactional
    public PlaylistViewDto readById(Integer id) {
        Playlist playlist = repositoryChecker.getPlaylistIfExists(id);

        return playlistMapper.toViewDto(playlist);
    }

    @Transactional
    public PlaylistViewDto create(PlaylistDto playlistDto) {
        if (playlistDto.isNotPrivateOrPublic()) {
            throw new IllegalArgumentException("Playlist type must be \"private\" or \"public\"");
        }

        User user = repositoryChecker.getCurrentUser();

        Playlist playlist = playlistMapper.toEntity(playlistDto);
        playlist.setCreatedDate(LocalDate.now());
        playlist.setUpdatedDate(LocalDate.now());
        playlist = playlistRepository.save(playlist);

        user.addOwnedPlaylist(playlist);

        return playlistMapper.toViewDto(playlist);
    }

    @Transactional
    public PlaylistViewDto update(Integer id, PlaylistDto playlistDto) {
        if (playlistDto.isNotPrivateOrPublic()) {
            throw new IllegalArgumentException("Playlist type must be \"private\" or \"public\"");
        }

        Playlist playlist = repositoryChecker.getPlaylistIfExists(id);
        checkIfOwner(jwtService.getCurrentUserId(), playlist);

        playlist.setName(playlistDto.getName());
        playlist.setType(playlistDto.getType());
        playlist.setUpdatedDate(LocalDate.now());

        return playlistMapper.toViewDto(playlist);
    }

    @Transactional
    public PlaylistViewDto addSongToPlaylist(Integer playlistId, Integer songId) {
        Playlist playlist = repositoryChecker.getPlaylistIfExists(playlistId);
        Song song = repositoryChecker.getSongIfExists(songId);
        checkIfOwner(jwtService.getCurrentUserId(), playlist);

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
        checkIfOwner(jwtService.getCurrentUserId(), playlist);

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
        checkIfOwner(jwtService.getCurrentUserId(), playlist);

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
        checkIfOwner(jwtService.getCurrentUserId(), playlist);

        List<Song> songs = playlist.getSongsInPlaylist();
        checkPositionsInRangeValid(oldPosition, newPosition, songs);
        checkSongPositionValid(songId, oldPosition, songs);

        if (!oldPosition.equals(newPosition)) {
            songs.remove(song);
            songs.add(newPosition - 1, song);
            playlist.setUpdatedDate(LocalDate.now());
        }

        return playlistMapper.toViewDto(playlist);
    }

    @Transactional
    public PlaylistViewDto delete(Integer id) {
        Playlist playlist = repositoryChecker.getPlaylistIfExists(id);
        User user = repositoryChecker.getCurrentUser();
        checkIfOwner(jwtService.getCurrentUserId(), playlist);

        user.removeOwnedPlaylist(playlist);

        Set<User> followers = playlist.getFollowerUsers();
        for (User follower : followers) {
            follower.getFollowedPlaylists().remove(playlist);
        }

        playlistRepository.delete(playlist);

        return playlistMapper.toViewDto(playlist);
    }

    @Transactional
    public PlaylistViewDto follow(Integer id) {
        Playlist playlist = repositoryChecker.getPlaylistIfExists(id);
        User user = repositoryChecker.getCurrentUser();

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

        return playlistMapper.toViewDto(playlist);
    }

    @Transactional
    public PlaylistViewDto unfollow(Integer id) {
        Playlist playlist = repositoryChecker.getPlaylistIfExists(id);
        User user = repositoryChecker.getCurrentUser();

        if (!playlist.getFollowerUsers().contains(user)) {
            throw new UnauthorizedException("You have not followed this playlist");
        }

        user.removeFollowedPlaylist(playlist);

        return playlistMapper.toViewDto(playlist);
    }
}

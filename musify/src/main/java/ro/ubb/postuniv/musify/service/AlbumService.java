package ro.ubb.postuniv.musify.service;

import static java.util.Optional.*;
import static ro.ubb.postuniv.musify.utils.checkers.PositionChecker.*;
import static ro.ubb.postuniv.musify.utils.checkers.UserChecker.*;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.postuniv.musify.dto.AlbumDetailViewDto;
import ro.ubb.postuniv.musify.dto.AlbumDto;
import ro.ubb.postuniv.musify.dto.AlbumViewDto;
import ro.ubb.postuniv.musify.mapper.AlbumMapper;
import ro.ubb.postuniv.musify.model.Album;
import ro.ubb.postuniv.musify.model.Artist;
import ro.ubb.postuniv.musify.model.Band;
import ro.ubb.postuniv.musify.model.Song;
import ro.ubb.postuniv.musify.repository.AlbumRepository;
import ro.ubb.postuniv.musify.repository.SongRepository;
import ro.ubb.postuniv.musify.security.JwtService;
import ro.ubb.postuniv.musify.utils.checkers.RepositoryChecker;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final JwtService jwtService;
    private final RepositoryChecker repositoryChecker;
    private final AlbumRepository albumRepository;
    private final SongRepository songRepository;
    private final AlbumMapper albumMapper;

    @Transactional
    public List<AlbumViewDto> readAll() {
        List<Album> albums = albumRepository.findAll();
        return albumMapper.toViewDtos(albums);
    }

    @Transactional
    public AlbumDetailViewDto readById(Integer id) {
        Album album = repositoryChecker.getAlbumIfExists(id);
        return albumMapper.toDetailViewDto(album);
    }

    @Transactional
    public List<AlbumDto> readAllByArtistId(Integer id) {
        Artist artist = repositoryChecker.getArtistIfExists(id);
        return albumMapper.toDtos(new ArrayList<>(artist.getArtistAlbums()));
    }

    @Transactional
    public AlbumDto create(AlbumDto albumDto) {
        validateAdminRole(jwtService.getCurrentUserRole());
        if (albumDto.isTwoOwnersIdSet()) {
            throw new IllegalArgumentException("One of artist id or band id must be set, the other must remain null");
        }

        Album album = albumMapper.toEntity(albumDto);
        albumRepository.save(album);

        addArtistOrBandById(album, albumDto);

        ofNullable(albumDto.getSongIds())
                .ifPresent(songs -> {
                    if (!songs.isEmpty()) {
                        addSongsById(album, albumDto);
                    }
                });

        return albumMapper.toDto(album);
    }

    @Transactional
    public AlbumDto update(Integer id, AlbumDto albumDto) {
        validateAdminRole(jwtService.getCurrentUserRole());
        if (albumDto.isTwoOwnersIdSet()) {
            throw new IllegalArgumentException("One of artist id or band id must be set, the other must remain null");
        }

        Album album = repositoryChecker.getAlbumIfExists(id);

        addArtistOrBandById(album, albumDto);
        album.setTitle(albumDto.getTitle());
        album.setDescription(albumDto.getDescription());
        album.setGenre(albumDto.getGenre());
        album.setReleaseDate(albumDto.getReleaseDate());
        album.setLabel(albumDto.getLabel());

        ofNullable(albumDto.getSongIds())
                .ifPresent(songs -> {
                    if (!songs.isEmpty()) {
                        clearSongs(album);
                        addSongsById(album, albumDto);
                    }
                });

        return albumMapper.toDto(album);
    }

    @Transactional
    public AlbumDetailViewDto addSong(Integer albumId, Integer songId) {
        validateAdminRole(jwtService.getCurrentUserRole());

        Album album = repositoryChecker.getAlbumIfExists(albumId);
        Song song = repositoryChecker.getSongIfExists(songId);

        if (!album.getSongs().contains(song)) {
            album.addSong(song);
        }

        return albumMapper.toDetailViewDto(album);
    }

    @Transactional
    public AlbumDetailViewDto removeSong(Integer albumId, Integer songId) {
        validateAdminRole(jwtService.getCurrentUserRole());

        Album album = repositoryChecker.getAlbumIfExists(albumId);
        Song song = repositoryChecker.getSongIfExists(songId);

        if (album.getSongs().contains(song)) {
            album.removeSong(song);
        }

        return albumMapper.toDetailViewDto(album);
    }

    @Transactional
    public AlbumDetailViewDto changeSongOrder(Integer albumId, Integer songId, Integer oldPosition, Integer newPosition) {
        validateAdminRole(jwtService.getCurrentUserRole());

        Album album = repositoryChecker.getAlbumIfExists(albumId);
        Song song = repositoryChecker.getSongIfExists(songId);

        List<Song> songs = album.getSongs();
        checkPositionsInRangeValid(oldPosition, newPosition, songs);
        checkSongPositionValid(songId, oldPosition, songs);

        if (!oldPosition.equals(newPosition)) {
            songs.remove(song);
            songs.add(newPosition - 1, song);
        }

        return albumMapper.toDetailViewDto(album);
    }



    private void addSongsById(Album album, AlbumDto albumDto) {
        List<Song> songs = (List<Song>) songRepository.findAllById(albumDto.getSongIds());
        for (Song song : songs) {
            if (!album.getSongs().contains(song)) {
                album.addSong(song);
            }
        }
    }

    private void clearSongs(Album album) {
        for (Song song : album.getSongs()) {
            song.getAlbums().remove(album);
        }

        album.getSongs().clear();
    }

    private void addArtistOrBandById(Album album, AlbumDto albumDto) {
        if (albumDto.getArtistId() != null && albumDto.getArtistId() != 0 && !albumDto.getArtistId().equals(album.getArtistId())) {
            Artist newArtist = repositoryChecker.getArtistIfExists(albumDto.getArtistId());

            ofNullable(album.getArtist()).ifPresent(previousArtist -> previousArtist.removeAlbum(album));
            newArtist.addAlbum(album);
        } else if (albumDto.getBandId() != null && albumDto.getBandId() != 0 && !albumDto.getBandId().equals(album.getBandId())) {
            Band newBand = repositoryChecker.getBandIfExists(albumDto.getArtistId());

            ofNullable(album.getBand()).ifPresent(previousBand -> previousBand.removeAlbum(album));
            newBand.addAlbum(album);
        }
    }
}

package ro.ubb.postuniv.musify.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.postuniv.musify.dto.AlbumDetailViewDto;
import ro.ubb.postuniv.musify.dto.AlbumDto;
import ro.ubb.postuniv.musify.dto.AlbumViewDto;
import ro.ubb.postuniv.musify.dto.SongViewDto;
import ro.ubb.postuniv.musify.mapper.AlbumMapper;
import ro.ubb.postuniv.musify.mapper.SongMapper;
import ro.ubb.postuniv.musify.model.Album;
import ro.ubb.postuniv.musify.model.Artist;
import ro.ubb.postuniv.musify.model.Band;
import ro.ubb.postuniv.musify.model.Song;
import ro.ubb.postuniv.musify.repository.AlbumRepository;
import ro.ubb.postuniv.musify.repository.SongRepository;
import ro.ubb.postuniv.musify.utils.RepositoryChecker;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final RepositoryChecker repositoryChecker;
    private final AlbumRepository albumRepository;
    private final SongRepository songRepository;
    private final AlbumMapper albumMapper;
    private final SongMapper songMapper;

    //TODO 24/07/2023 stefan.bertici: move to song service / controller
    @Transactional
    public List<SongViewDto> readSongsByAlbumId(Integer id) {
        Album album = repositoryChecker.getAlbumIfExists(id);
        List<Song> songs = album.getSongs();

        return songMapper.toViewDtos(songs);
    }

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
    public AlbumDto create(AlbumDto albumDto) {
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

    //TODO 23/07/2023 stefan.bertici: new endpoint for add and remove to / from album
    // this way is easier than rewriting the current logic to support reactive forms - i think

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
        if (albumDto.getArtistId() != null && albumDto.getArtistId() != 0) {
            Integer id = albumDto.getArtistId();
            Artist artist = repositoryChecker.getArtistIfExists(id);
            artist.addAlbum(album);
        } else if (albumDto.getBandId() != null && albumDto.getBandId() != 0) {
            Integer id = albumDto.getBandId();
            Band band = repositoryChecker.getBandIfExists(id);
            band.addAlbum(album);
        }
    }
}

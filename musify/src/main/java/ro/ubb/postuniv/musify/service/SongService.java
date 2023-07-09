package ro.ubb.postuniv.musify.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.postuniv.musify.dto.SongDto;
import ro.ubb.postuniv.musify.mapper.SongMapper;
import ro.ubb.postuniv.musify.model.AlternativeSongTitle;
import ro.ubb.postuniv.musify.model.Artist;
import ro.ubb.postuniv.musify.model.Song;
import ro.ubb.postuniv.musify.repository.AlternativeSongTitleRepository;
import ro.ubb.postuniv.musify.repository.ArtistRepository;
import ro.ubb.postuniv.musify.repository.SongRepository;
import ro.ubb.postuniv.musify.utils.RepositoryChecker;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SongService {

    private final RepositoryChecker repositoryChecker;
    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;
    private final AlternativeSongTitleRepository alternativeSongTitleRepository;
    private final SongMapper songMapper;

    @Transactional
    public SongDto createSong(SongDto songDto) {
        Song song = songMapper.toEntity(songDto);
        song = songRepository.save(song);

        if (!songDto.getAlternativeTitles().isEmpty()) {
            addAlternativeTitles(song, songDto);
        }

        if (!songDto.getComposersIds().isEmpty()) {
            addComposersById(song, songDto);
        }

        return songMapper.toDto(song);
    }

    @Transactional
    public SongDto updateSong(Integer id, SongDto songDto) {
        Song song = repositoryChecker.getSongIfExists(id);

        song.setTitle(songDto.getTitle());
        song.setDuration(songDto.getDuration());
        song.setCreatedDate(songDto.getCreatedDate());

        if (!songDto.getAlternativeTitles().isEmpty()) {
            clearAlternativeTitles(song);
            addAlternativeTitles(song, songDto);
        }

        if (!songDto.getComposersIds().isEmpty()) {
            clearComposers(song);
            addComposersById(song, songDto);
        }

        return songMapper.toDto(song);
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
}

package ro.ubb.postuniv.musify.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.postuniv.musify.dto.SongDTO;
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
@AllArgsConstructor
public class SongService {
    private final RepositoryChecker repositoryChecker;
    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;
    private final AlternativeSongTitleRepository alternativeSongTitleRepository;
    private final SongMapper songMapper;

    @Transactional
    public SongDTO createSong(SongDTO songDTO) {
        Song song = songMapper.toEntity(songDTO);
        song = songRepository.save(song);

        if (!songDTO.getAlternativeTitles().isEmpty()) {
            addAlternativeTitles(song, songDTO);
        }

        if (!songDTO.getComposersIds().isEmpty()) {
            addComposersById(song, songDTO);
        }

        return songMapper.toDto(song);
    }

    @Transactional
    public SongDTO updateSong(Integer id, SongDTO songDTO) {
        Song song = repositoryChecker.getSongIfExists(id);

        song.setTitle(songDTO.getTitle());
        song.setDuration(songDTO.getDuration());
        song.setCreatedDate(songDTO.getCreatedDate());

        if (!songDTO.getAlternativeTitles().isEmpty()) {
            clearAlternativeTitles(song);
            addAlternativeTitles(song, songDTO);
        }

        if (!songDTO.getComposersIds().isEmpty()) {
            clearComposers(song);
            addComposersById(song, songDTO);
        }

        return songMapper.toDto(song);
    }

    private void clearComposers(Song song) {
        for (Artist artist : song.getComposers()) {
            artist.getComposedSongs().remove(song);
        }

        song.getComposers().clear();
    }

    private void addComposersById(Song song, SongDTO songDTO) {
        List<Artist> artists = (List<Artist>) artistRepository.findAllById(songDTO.getComposersIds());
        for (Artist artist : artists) {
            artist.addComposedSong(song);
        }
    }

    private void addAlternativeTitles(Song song, SongDTO songDTO) {
        for (String title : songDTO.getAlternativeTitles()) {
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

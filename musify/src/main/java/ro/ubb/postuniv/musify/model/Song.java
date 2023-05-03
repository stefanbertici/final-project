package ro.ubb.postuniv.musify.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "songs")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private Time duration;
    @Column(name = "created_date")
    private Date createdDate;

    @OneToMany(mappedBy = "song")
    Set<AlternativeSongTitle> alternativeSongTitles = new HashSet<>();

    @ManyToMany(mappedBy = "songs")
    private Set<Album> albums = new HashSet<>();

    @ManyToMany(mappedBy = "composedSongs")
    private Set<Artist> composers = new HashSet<>();

    @ManyToMany(mappedBy = "songsInPlaylist")
    private Set<Playlist> playlists = new HashSet<>();

    public List<String> getAlternativeSongTitlesList() {
        return alternativeSongTitles
                .stream()
                .map(AlternativeSongTitle::getAlternativeTitle)
                .collect(Collectors.toList());
    }

    public List<String> getAlbumsTitlesList() {
        return albums
                .stream()
                .map(Album::getTitle)
                .collect(Collectors.toList());
    }

    public List<Integer> getComposersIdsList() {
        return composers
                .stream()
                .map(Artist::getId)
                .collect(Collectors.toList());
    }

    public List<String> getComposersStageNamesList() {
        return composers
                .stream()
                .map(Artist::getStageName)
                .collect(Collectors.toList());
    }

    public void addAlternativeSongTitle(AlternativeSongTitle alternativeSongTitle) {
        alternativeSongTitles.add(alternativeSongTitle);
        alternativeSongTitle.setSong(this);
    }

    public void removeAlternativeSongTitle(AlternativeSongTitle alternativeSongTitle) {
        alternativeSongTitles.remove(alternativeSongTitle);
        alternativeSongTitle.setSong(null);
    }
}

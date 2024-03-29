package ro.ubb.postuniv.musify.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "songs")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private LocalTime duration;
    @Column(name = "created_date")
    private LocalDate createdDate;

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
                .toList();
    }

    public List<String> getAlbumsTitlesList() {
        return albums.stream()
                .map(Album::getTitle)
                .toList();
    }

    public List<Integer> getComposersIdsList() {
        return composers
                .stream()
                .map(Artist::getId)
                .toList();
    }

    public List<String> getComposersStageNamesList() {
        return composers
                .stream()
                .map(Artist::getStageName)
                .toList();
    }

    public void addAlternativeSongTitle(AlternativeSongTitle alternativeSongTitle) {
        alternativeSongTitles.add(alternativeSongTitle);
        alternativeSongTitle.setSong(this);
    }

    public void removeAlternativeSongTitle(AlternativeSongTitle alternativeSongTitle) {
        alternativeSongTitles.remove(alternativeSongTitle);
        alternativeSongTitle.setSong(null);
    }

    public void removeFromPlaylists() {
        this.getPlaylists().forEach(p -> p.getSongsInPlaylist().remove(this));
        this.playlists.clear();
    }

    public String getArtistName() {
        StringBuilder sb = new StringBuilder();

        this.albums.forEach(a -> {
            if (Objects.nonNull(a.getArtist()) && !sb.toString().contains(a.getArtist().getStageName())) {
                sb.append(a.getArtist().getStageName()).append(", ");
            }

            if (Objects.nonNull(a.getBand()) && !sb.toString().contains(a.getBand().getBandName())) {
                sb.append(a.getBand().getBandName()).append(", ");
            }
        });

        if (!sb.isEmpty()) {
            sb.deleteCharAt(sb.lastIndexOf(", "));
        }

        return sb.toString();
    }
}

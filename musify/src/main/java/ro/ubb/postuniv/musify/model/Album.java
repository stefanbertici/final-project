package ro.ubb.postuniv.musify.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "albums")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    @Column(length = 1020)
    private String description;
    private String genre;
    @Column(name = "release_date")
    private LocalDate releaseDate;
    private String label;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @ManyToOne
    @JoinColumn(name = "band_id")
    private Band band;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(name = "albums_songs",
            joinColumns = {@JoinColumn(name = "album_id")},
            inverseJoinColumns = {@JoinColumn(name = "song_id")})
    @OrderColumn(name = "song_order")
    private List<Song> songs = new ArrayList<>();

    public List<Integer> getSongIds() {
        return songs.stream()
                .map(Song::getId)
                .toList();
    }

    public Integer getArtistId() {
        if (artist != null) {
            return artist.getId();
        }

        return 0;
    }

    public Integer getBandId() {
        if (band != null) {
            return band.getId();
        }

        return 0;
    }

    public void addSong(Song song) {
        if (songs.contains(song)) {
            throw new IllegalArgumentException("Song already in the album");
        }

        songs.add(song);
        song.getAlbums().add(this);
    }

    public void removeSong(Song song) {
        if (!songs.contains(song)) {
            throw new IllegalArgumentException("Song is not in the album");
        }

        songs.remove(song);
        song.getAlbums().remove(this);
    }

    public Integer getOverallArtistId() {
        Integer overallId;

        if (Objects.nonNull(artist)) {
            overallId = artist.getId();
        } else if (Objects.nonNull(band)) {
            overallId = band.getId();
        } else {
            overallId = 0;
        }

        return overallId;
    }

    public String getOverallArtistName() {
        String overallName = "";

        if (Objects.nonNull(artist)) {
            overallName = artist.getStageName();
        } else if (Objects.nonNull(band)) {
            overallName = band.getBandName();
        }

        return overallName;
    }
}

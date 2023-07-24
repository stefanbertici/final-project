package ro.ubb.postuniv.musify.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "albums")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
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

        return null;
    }

    public Integer getBandId() {
        if (band != null) {
            return band.getId();
        }

        return null;
    }

    public void addSong(Song song) {
        if (songs.contains(song)) {
            throw new IllegalArgumentException("Song already in the album");
        }

        songs.add(song);
        song.getAlbums().add(this);
    }

    public void removeSong(Song song) {
        songs.remove(song);
        song.getAlbums().remove(this);
    }

    public String getArtistName() {
        String name;

        if (Objects.nonNull(artist)) {
            name = artist.getStageName();
        } else if (Objects.nonNull(band)) {
            name = band.getBandName();
        } else {
            name = "Album does not have an artist set";
        }

        return name;
    }
}

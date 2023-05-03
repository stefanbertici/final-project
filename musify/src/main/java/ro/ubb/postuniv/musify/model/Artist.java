package ro.ubb.postuniv.musify.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

/* for base hibernate operations, not used in the latest version of this project
@NamedQueries({
        @NamedQuery(name = "findAllArtists", query = "from Artist"),
        @NamedQuery(name = "findArtistById", query = "from Artist where id = :id")
})*/
@Getter
@Setter
@Entity
@Table(name = "artists")
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "stage_name")
    private String stageName;
    private Date birthday;
    @Column(name = "activity_start_date")
    private String activityStartDate;
    @Column(name = "activity_end_date")
    private String activityEndDate;

    @ManyToMany(mappedBy = "artists")
    private Set<Band> bands = new HashSet<>();

    @OneToMany(mappedBy = "artist")
    private Set<Album> artistAlbums = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "artists_songs",
            joinColumns = { @JoinColumn(name = "artist_id") },
            inverseJoinColumns = { @JoinColumn(name = "song_id") })
    private Set<Song> composedSongs = new HashSet<>();

    public void addAlbum(Album album) {
        artistAlbums.add(album);
        album.setArtist(this);
    }

    public void removeAlbum(Album album) {
        artistAlbums.remove(album);
        album.setArtist(null);
    }

    public void addComposedSong(Song song) {
        composedSongs.add(song);
        song.getComposers().add(this);
    }

    public void removeComposedSong(Song song) {
        composedSongs.remove(song);
        song.getComposers().remove(this);
    }
}

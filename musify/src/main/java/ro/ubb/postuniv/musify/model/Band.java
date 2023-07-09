package ro.ubb.postuniv.musify.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "bands")
public class Band{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "band_name")
    private String bandName;
    private String location;
    @Column(name = "activity_start_date")
    private String activityStartDate;
    @Column(name = "activity_end_date")
    private String activityEndDate;

    @ManyToMany
    @JoinTable(name = "bands_artists",
            joinColumns = { @JoinColumn(name = "band_id") },
            inverseJoinColumns = { @JoinColumn(name = "artist_id") })
    private Set<Artist> artists = new HashSet<>();

    @OneToMany(mappedBy = "band")
    private Set<Album> bandAlbums = new HashSet<>();

    public List<Integer> getArtistsIds() {
        List<Integer> ids = new ArrayList<>();
        for (Artist artist : artists) {
            ids.add(artist.getId());
        }

        return ids;
    }

    public void addArtist(Artist artist) {
        artists.add(artist);
        artist.getBands().add(this);
    }

    public void removeArtist(Artist artist) {
        artists.remove(artist);
        artist.getBands().remove(this);
    }

    public void addAlbum(Album album) {
        bandAlbums.add(album);
        album.setBand(this);
    }

    public void removeAlbum(Album album) {
        bandAlbums.remove(album);
        album.setBand(null);
    }
}

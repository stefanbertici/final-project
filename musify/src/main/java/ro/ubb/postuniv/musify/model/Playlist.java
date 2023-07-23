package ro.ubb.postuniv.musify.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Optional.ofNullable;

@Getter
@Setter
@Entity
@Table(name = "playlists")
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String type;
    @Column(name = "created_date")
    private LocalDate createdDate;
    @Column(name = "updated_date")
    private LocalDate updatedDate;

    @ManyToOne
    @JoinColumn(name = "owner_user_id")
    private User ownerUser;

    @ManyToMany(mappedBy = "followedPlaylists")
    private Set<User> followerUsers = new HashSet<>();

    @ManyToMany()
    @JoinTable(name = "playlists_songs",
            joinColumns = { @JoinColumn(name = "playlist_id") },
            inverseJoinColumns = { @JoinColumn(name = "song_id") })
    @OrderColumn(name = "song_order")
    private List<Song> songsInPlaylist = new ArrayList<>();

    public Integer getOwnerUserId() {
        return ofNullable(ownerUser)
                .map(User::getId)
                .orElse(0);
    }

    public void addSong(Song song) {
        if(songsInPlaylist.contains(song)){
            throw new IllegalArgumentException("Song already in the playlist");
        }

        songsInPlaylist.add(song);
        song.getPlaylists().add(this);
    }

    public void removeSong(Song song) {
        if(songsInPlaylist.contains(song)){
            songsInPlaylist.remove(song);
            song.getPlaylists().remove(this);
        }
    }
}

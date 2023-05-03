package ro.ubb.postuniv.musify.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.*;

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
    private Date createdDate;
    @Column(name = "updated_date")
    private Date updatedDate;

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
        return ownerUser.getId();
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

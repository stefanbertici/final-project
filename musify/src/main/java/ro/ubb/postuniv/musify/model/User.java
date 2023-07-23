package ro.ubb.postuniv.musify.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String email;
    @Column(name = "encrypted_password")
    private String encryptedPassword;
    private String country;
    private String role;
    private String status;

    @OneToMany(mappedBy = "ownerUser", cascade = CascadeType.REMOVE)
    Set<Playlist> ownedPlaylists = new HashSet<>();

    @ManyToMany()
    @JoinTable(name = "users_playlists",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "playlist_id") })
    private Set<Playlist> followedPlaylists = new HashSet<>();

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public void addFollowedPlaylist(Playlist playlist) {
        followedPlaylists.add(playlist);
        playlist.getFollowerUsers().add(this);
    }

    public void removeFollowedPlaylist(Playlist playlist) {
        followedPlaylists.remove(playlist);
        playlist.getFollowerUsers().remove(this);
    }

    public void addOwnedPlaylist(Playlist playlist) {
        ownedPlaylists.add(playlist);
        playlist.setOwnerUser(this);
    }

    public void removeOwnedPlaylist(Playlist playlist) {
        ownedPlaylists.remove(playlist);
        playlist.setOwnerUser(null);
    }
}

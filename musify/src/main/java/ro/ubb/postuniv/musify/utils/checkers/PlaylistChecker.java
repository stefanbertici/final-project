package ro.ubb.postuniv.musify.utils.checkers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ro.ubb.postuniv.musify.model.Playlist;
import ro.ubb.postuniv.musify.model.User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlaylistChecker {

    public static Boolean isFollowableByUser(Playlist playlist, User user) {
        boolean notOwner = !playlist.getOwnerUser().getId().equals(user.getId());
        boolean notPrivate = !playlist.getType().equals("private");
        boolean notAlreadyFollowed = !user.getFollowedPlaylists().contains(playlist);
        return notOwner && notPrivate && notAlreadyFollowed;
    }

    public static Boolean isUnfollowableByUser(Playlist playlist, User user) {
        boolean notOwner = !playlist.getOwnerUser().getId().equals(user.getId());
        boolean notPrivate = !playlist.getType().equals("private");
        boolean alreadyFollowed = user.getFollowedPlaylists().contains(playlist);
        return notOwner && notPrivate && alreadyFollowed;
    }
}

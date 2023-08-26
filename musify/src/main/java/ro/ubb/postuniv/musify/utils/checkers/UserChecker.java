package ro.ubb.postuniv.musify.utils.checkers;

import static ro.ubb.postuniv.musify.utils.constants.UserRole.*;
import static ro.ubb.postuniv.musify.utils.constants.UserStatus.*;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ro.ubb.postuniv.musify.exception.UnauthorizedException;
import ro.ubb.postuniv.musify.model.Playlist;
import ro.ubb.postuniv.musify.model.User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserChecker {

    public static void validateAdminRole(String currentUserRole) {
        if (isCurrentUserNotAdmin(currentUserRole)) {
            throw new UnauthorizedException("Only admins can perform this operation");
        }
    }

    public static boolean isCurrentUserNotAdmin(String role) {
        return !role.equals(ADMIN.value);
    }

    public static boolean isActive(User user) {
        return user.getStatus().equals(ACTIVE.value);
    }

    public static boolean canLogin(User user, String encryptedPassword) {
        return user.getEncryptedPassword().equals(encryptedPassword);
    }

    public static boolean isOperationOnSelf(Integer currentUserId, Integer id) {
        return currentUserId.equals(id);
    }

    public static boolean isNotOwnerOfPlaylist(Integer currentUserId, Playlist playlist) {
        return !currentUserId.equals(playlist.getOwnerUserId());
    }

    public static void checkIfOwner(Integer currentUserId, Playlist playlist) {
        if (playlist.getOwnerUserId().intValue() != currentUserId.intValue()) {
            throw new UnauthorizedException("You can't modify playlists you do not own");
        }
    }
}

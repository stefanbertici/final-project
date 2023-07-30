package ro.ubb.postuniv.musify.utils.checkers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ro.ubb.postuniv.musify.model.Song;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PositionChecker {

    public static void checkSongPositionValid(Integer songId, Integer oldPosition, List<Song> songs) {
        if (songs.get(oldPosition - 1).getId().intValue() != songId.intValue()) {
            throw new IllegalArgumentException("The song introduced is not in the correct position");
        }
    }

    public static void checkPositionsInRangeValid(Integer oldPosition, Integer newPosition, List<Song> songs) {
        if (oldPosition < 1 || oldPosition > songs.size() || newPosition < 1 || newPosition > songs.size()) {
            throw new IllegalArgumentException("The given positions are not in range.");
        }
    }
}

package ro.ubb.postuniv.musify.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistCategorizedViewDto {

    private List<PlaylistViewDto> ownedPlaylists;
    private List<PlaylistViewDto> followedPlaylists;
}

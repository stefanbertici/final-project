package ro.ubb.postuniv.musify.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchViewDto {

    private List<PlaylistViewDto> playlists;
    private List<ArtistViewDto> artists;
    private List<BandDto> bands;
    private List<AlbumViewDto> albums;
    private List<SongViewDto> songs;
}

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
public class SearchViewDto {

    private List<ArtistDto> artists;
    private List<BandDto> bands;
    private List<AlbumDto> albums;
    private List<SongViewDto> songs;
}

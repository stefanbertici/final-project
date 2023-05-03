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
public class SearchViewDTO {
    private List<ArtistDTO> artists;
    private List<BandDTO> bands;
    private List<AlbumDTO> albums;
    private List<SongViewDTO> songs;
}

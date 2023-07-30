package ro.ubb.postuniv.musify.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlbumDetailViewDto {

    private Integer id;
    // in this context artist can be a singular artist or a band consisting of multiple artists
    private Integer artistId;
    private String artist;
    private String title;
    private String description;
    private String genre;
    private LocalDate releaseDate;
    private String label;
    private List<SongViewDto> songs;
}

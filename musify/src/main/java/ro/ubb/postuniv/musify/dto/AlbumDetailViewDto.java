package ro.ubb.postuniv.musify.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

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

package ro.ubb.postuniv.musify.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlbumViewDto {

    private Integer id;
    // in this context artist can be a singular artist or a band consisting of multiple artists
    private String artist;
    private String title;
    private String genre;
}

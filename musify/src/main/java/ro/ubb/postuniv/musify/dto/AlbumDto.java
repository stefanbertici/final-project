package ro.ubb.postuniv.musify.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlbumDto {

    private Integer id;
    private Integer artistId;
    private Integer bandId;
    @NotBlank(message = "Title cannot be blank")
    private String title;
    private String description;
    private String genre;
    private LocalDate releaseDate;
    private String label;
    private List<Integer> songIds;

    @JsonIgnore
    public boolean isTwoOwnersIdSet() {
        boolean artistSet;
        boolean bandSet;

        artistSet = artistId != null && artistId != 0;
        bandSet = bandId != null && bandId != 0;

        return artistSet && bandSet;
    }
}

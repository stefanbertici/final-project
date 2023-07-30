package ro.ubb.postuniv.musify.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SongViewDto {

    private Integer id;
    // artist in this context can mean an actual singular artist or a band
    private String artist;
    private String title;
    private List<String> alternativeTitles;
    private LocalTime duration;
    private LocalDate createdDate;
    private List<String> composers;
    private List<String> albums;
}

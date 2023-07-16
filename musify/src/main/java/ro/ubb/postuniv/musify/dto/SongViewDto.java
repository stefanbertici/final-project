package ro.ubb.postuniv.musify.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

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
    private Time duration;
    private Date createdDate;
    private List<String> composers;
    private List<String> albums;
}

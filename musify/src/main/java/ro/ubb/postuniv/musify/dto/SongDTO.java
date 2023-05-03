package ro.ubb.postuniv.musify.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SongDTO {
    private Integer id;
    @NotBlank(message = "Title cannot be blank")
    private String title;
    private Time duration;
    private Date createdDate;
    private List<String> alternativeTitles;
    private List<Integer> composersIds;
}

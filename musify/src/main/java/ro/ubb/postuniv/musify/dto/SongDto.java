package ro.ubb.postuniv.musify.dto;

import java.time.LocalDate;
import java.time.LocalTime;
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
public class SongDto {

    private Integer id;
    @NotBlank(message = "Title cannot be blank")
    private String title;
    private LocalTime duration;
    private LocalDate createdDate;
    private List<String> alternativeTitles;
    private List<Integer> composersIds;
}

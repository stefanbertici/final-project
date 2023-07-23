package ro.ubb.postuniv.musify.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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

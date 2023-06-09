package ro.ubb.postuniv.musify.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArtistDto {

    private Integer id;
    private String firstName;
    private String lastName;
    @NotBlank(message = "Stage name cannot be blank")
    private String stageName;
    private Date birthday;
    private String activityStartDate;
    private String activityEndDate;
}

package ro.ubb.postuniv.musify.dto;

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
public class BandDto {

    private Integer id;
    @NotBlank(message = "Band name cannot be blank")
    private String bandName;
    private String location;
    private String activityStartDate;
    private String activityEndDate;
    private List<Integer> bandMembersIds;
}

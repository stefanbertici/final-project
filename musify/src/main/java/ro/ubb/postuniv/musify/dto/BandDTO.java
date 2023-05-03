package ro.ubb.postuniv.musify.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BandDTO {
    private Integer id;
    @NotBlank(message = "Band name cannot be blank")
    private String bandName;
    private String location;
    private String activityStartDate;
    private String activityEndDate;
    private List<Integer> bandMembersIds;
}

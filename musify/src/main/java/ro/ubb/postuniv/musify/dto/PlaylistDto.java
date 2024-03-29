package ro.ubb.postuniv.musify.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.sql.Date;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistDto {

    private Integer id;
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @NotBlank(message = "Type cannot be blank")
    private String type;
    private Date createdDate;
    private Date updatedDate;
    private Integer ownerUserId;

    @JsonIgnore
    public boolean isNotPrivateOrPublic() {
        return !(type.equals("private") || type.equals("public"));
    }
}

package ro.ubb.postuniv.musify.dto;

import java.sql.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistViewDto {

    private Integer id;
    private String name;
    private String type;
    private Date createdDate;
    private Date updatedDate;
    private Integer ownerUserId;
    private String ownerUserName;
    private List<SongViewDto> songs;
}

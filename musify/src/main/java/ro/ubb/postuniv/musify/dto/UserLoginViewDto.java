package ro.ubb.postuniv.musify.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserLoginViewDto {

    private Integer id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private String country;
    private String role;
    private String status;
    private String accessToken;
}

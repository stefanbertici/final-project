package ro.ubb.postuniv.musify.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {
    @NotBlank(message = "Email cannot be blank")
    private String email;
    @NotBlank(message = "Password cannot be blank")
    private String password;
}

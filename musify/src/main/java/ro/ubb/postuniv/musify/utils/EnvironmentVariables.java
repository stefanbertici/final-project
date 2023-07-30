package ro.ubb.postuniv.musify.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EnvironmentVariables {

  private String secret;
  private String issuer;
}

package ro.ubb.postuniv.musify.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.ubb.postuniv.musify.utils.EnvironmentVariables;

@Getter
@Configuration
public class EnvPropertiesConfig {

    @Value("${secret}")
    private String secret;

    @Value("${issuer}")
    private String issuer;

    @Bean
    public EnvironmentVariables environmentVariables() {
        EnvironmentVariables env = new EnvironmentVariables();

        env.setSecret(secret);
        env.setIssuer(issuer);

        return env;
    }
}

package ro.ubb.postuniv.musify.security;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final InMemoryTokenBlacklist tokenBlacklist;
    private final JwtService jwtService;

    @Autowired
    public WebSecurity(InMemoryTokenBlacklist tokenBlacklist, JwtService jwtService) {
        super();
        this.tokenBlacklist = tokenBlacklist;
        this.jwtService = jwtService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers("/**/swagger-resources", "/**/swagger-resources/**", "/**/swagger-ui",
                        "/**/swagger-ui/**", "/**/swagger-ui.html", "/**/swagger-ui.html/**", "/**/v3/api-docs/**").permitAll()
                .antMatchers(HttpMethod.POST, "/users/register", "/users/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/users/login")
                .and()
                .cors()
                .and()
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), tokenBlacklist, jwtService))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://localhost:8080"));
        configuration.setAllowedMethods(Arrays.asList("GET", "PUT", "POST", "DELETE"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

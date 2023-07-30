package ro.ubb.postuniv.musify.security;

import static ro.ubb.postuniv.musify.utils.constants.Constants.*;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ro.ubb.postuniv.musify.utils.EnvironmentVariables;

@RequiredArgsConstructor
@Service
public class JwtService {

    private String secret;
    private String issuer;
    private final EnvironmentVariables environmentVariables;

    @PostConstruct
    private synchronized void init() {
        secret = environmentVariables.getSecret();
        issuer = environmentVariables.getIssuer();
    }

    public String generateToken(int id, String email, String role) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        Calendar c = Calendar.getInstance();
        Date currentDate = c.getTime();

        c.add(Calendar.HOUR, 24);
        Date expireDate = c.getTime();

        return JWT.create()
                .withIssuer(issuer)
                .withSubject(issuer)
                .withIssuedAt(currentDate)
                .withExpiresAt(expireDate)
                .withClaim(ID.value, id)
                .withClaim(EMAIL.value, email)
                .withClaim(ROLE.value, role)
                .sign(algorithm);
    }

    public Map<String, Object> validateToken(String jwtToken) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(issuer)
                .withSubject(issuer)
                .build();

        DecodedJWT decodedJWT = verifier.verify(jwtToken);
        Integer id = decodedJWT.getClaim(ID.value).asInt();
        String email = decodedJWT.getClaim(EMAIL.value).asString();
        String role = decodedJWT.getClaim(ROLE.value).asString();

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put(ID.value, id);
        userInfo.put(EMAIL.value, email);
        userInfo.put(ROLE.value, role);

        return userInfo;
    }

    public String extractTokenFromHeader(String header) {
        return header.replace("Bearer ", "").trim();
    }

    public Integer getCurrentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof Map) {
            return (Integer) ((Map<String, Object>) principal).get(ID.value);
        }

        throw new RuntimeException("Could not get current user's id from security context");
    }

    public String getCurrentUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof Map) {
            return (String) ((Map<String, Object>) principal).get(EMAIL.value);
        }

        throw new RuntimeException("Could not get current user's email from security context");
    }

    public String getCurrentUserRole() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof Map) {
            return (String) ((Map<String, Object>) principal).get(ROLE.value);
        }

        throw new RuntimeException("Could not get current user's role from security context");
    }
}

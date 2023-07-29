package ro.ubb.postuniv.musify.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtUtils {

    private static final String SECRET = "myMusifyApp2023";
    private static final String ISSUER = "musify";

    public static String generateToken(int id, String email, String role) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET);

        Calendar c = Calendar.getInstance();
        Date currentDate = c.getTime();

        c.add(Calendar.HOUR, 24);
        Date expireDate = c.getTime();

        return JWT.create()
                .withIssuer(ISSUER)
                .withSubject(ISSUER)
                .withIssuedAt(currentDate)
                .withExpiresAt(expireDate)
                .withClaim("id", id)
                .withClaim("email", email)
                .withClaim("role", role)
                .sign(algorithm);
    }

    public static Map<String, Object> validateToken(String jwtToken) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET);

        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(ISSUER)
                .withSubject(ISSUER)
                .build();

        DecodedJWT decodedJWT = verifier.verify(jwtToken);
        Integer id = decodedJWT.getClaim("id").asInt();
        String email = decodedJWT.getClaim("email").asString();
        String role = decodedJWT.getClaim("role").asString();

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", id);
        userInfo.put("email", email);
        userInfo.put("role", role);

        return userInfo;
    }

    public static String extractTokenFromHeader(String header) {
        return header.replace("Bearer ", "").trim();
    }

    public static Integer getCurrentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof Map) {
            return (Integer) ((Map<String, Object>) principal).get("id");
        }

        throw new RuntimeException("Could not get current user's id from security context");
    }

    public static String getCurrentUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof Map) {
            return (String) ((Map<String, Object>) principal).get("email");
        }

        throw new RuntimeException("Could not get current user's email from security context");
    }

    public static String getCurrentUserRole() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof Map) {
            return (String) ((Map<String, Object>) principal).get("role");
        }

        throw new RuntimeException("Could not get current user's role from security context");
    }
}

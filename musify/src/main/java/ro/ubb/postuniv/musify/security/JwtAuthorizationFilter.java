package ro.ubb.postuniv.musify.security;

import static ro.ubb.postuniv.musify.utils.constants.Constants.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    private final InMemoryTokenBlacklist tokenBlackList;
    private final JwtService jwtService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, InMemoryTokenBlacklist tokenBlackList, JwtService jwtService) {
        super(authenticationManager);
        this.tokenBlackList = tokenBlackList;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        String token = jwtService.extractTokenFromHeader(header);
        if (tokenBlackList.isBlackListed(token)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(token);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    // uses JWT to validate the token
    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        if (token != null) {
            Map<String, Object> userInfo = jwtService.validateToken(token);
            String id = String.valueOf(userInfo.get(ID.value));
            String email = (String) userInfo.get(EMAIL.value);
            String role = (String) userInfo.get(ROLE.value);

            if (id != null && email != null && role != null) {
                // new arraylist means authorities
                return new UsernamePasswordAuthenticationToken(userInfo, null, new ArrayList<>());
            }

            return null;
        }

        return null;
    }
}

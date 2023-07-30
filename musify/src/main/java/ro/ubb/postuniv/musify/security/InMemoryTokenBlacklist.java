package ro.ubb.postuniv.musify.security;

import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class InMemoryTokenBlacklist {

    private final Set<String> tokens;

    public InMemoryTokenBlacklist() {
        this.tokens = new HashSet<>();
    }

    public void blacklist(String token) {
        tokens.add(token);
    }

    public boolean isBlackListed(String token) {
        return tokens.contains(token);
    }
}

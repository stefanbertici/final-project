package ro.ubb.postuniv.musify.utils.constants;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserRole {

    USER("user"),
    ADMIN("admin");

    public final String value;
}

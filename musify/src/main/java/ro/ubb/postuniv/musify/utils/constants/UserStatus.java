package ro.ubb.postuniv.musify.utils.constants;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserStatus {

    ACTIVE("active"),
    INACTIVE("inactive");

    public final String value;
}

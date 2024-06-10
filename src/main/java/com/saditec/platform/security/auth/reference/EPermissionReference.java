package com.saditec.platform.security.auth.reference;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Getter
@AllArgsConstructor
public enum EPermissionReference {
    USER_ROLE(
            Set.of("user:read",
                    "educational_and_recreation:read",
                    "educational_and_recreation_type:read",
                    "reservation:read", "reservation:write")
    ),
    ADMIN_ROLE(
            Set.of("user:read", "user:write",
                    "educational_and_recreation:read",
                    "educational_and_recreation:write",
                    "educational_and_recreation_type:read",
                    "educational_and_recreation_type:write",
                    "reservation:read", "reservation:write",
                    "reservation_hour:read", "reservation_hour:write")
    );

    private final Set<String> permissions;
}

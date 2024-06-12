package com.saditec.platform.security.jwt.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserResponseToken {

    private String accessToken;
    private long expirationTime;
    private String tokenType;
}

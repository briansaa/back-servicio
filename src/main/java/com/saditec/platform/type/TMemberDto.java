package com.saditec.platform.type;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class TMemberDto {

    private String email;
    private String code;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String lastName;
    private String firstName;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<String> roles;
}

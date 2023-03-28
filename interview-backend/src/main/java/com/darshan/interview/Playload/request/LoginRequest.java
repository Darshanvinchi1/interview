package com.darshan.interview.Playload.request;

import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}

package com.darshan.interview.Playload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class SingupUser {

    @NotBlank
    @NotNull
    private String username;

    @NotNull
    @NotBlank
    private String password;

    @NotBlank
    @Email
    @NotNull
    private String email;

    @NotBlank
    @NotNull
    private String phoneNumber;

    private Set<String> role;
}

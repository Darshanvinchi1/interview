package com.darshan.interview.Playload.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {
    private Integer id;
    private String username;
    private String email;
    private List<String> roles;

    private String jwtCookie;
}

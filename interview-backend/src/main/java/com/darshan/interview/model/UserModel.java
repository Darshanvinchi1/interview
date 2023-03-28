package com.darshan.interview.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();


    public UserModel(String username, String encode, String email, String phoneNumber) {
        this.username = username;
        this.password = encode;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}

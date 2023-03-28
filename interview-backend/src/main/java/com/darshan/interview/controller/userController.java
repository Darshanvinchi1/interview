package com.darshan.interview.controller;

import com.darshan.interview.Playload.request.LoginRequest;
import com.darshan.interview.Playload.request.SingupUser;
import com.darshan.interview.Playload.response.JwtResponse;
import com.darshan.interview.Playload.response.MessageResponse;
import com.darshan.interview.Repository.RoleRepository;
import com.darshan.interview.Repository.UserRepository;
import com.darshan.interview.model.ERole;
import com.darshan.interview.model.Role;
import com.darshan.interview.model.UserModel;
import com.darshan.interview.security.jwt.JwtUtils;
import com.darshan.interview.security.services.UserDetailsImpl;

import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
public class userController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;


    @PostMapping("singin")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        if (!userRepository.existsByUsername(loginRequest.getUsername())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body("Username is not found!");
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SingupUser singupUser) {
        if (userRepository.existsByUsername(singupUser.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }
        if (userRepository.existsByEmail(singupUser.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        UserModel userModel = new UserModel(
                singupUser.getUsername(),
                encoder.encode(singupUser.getPassword()),
                singupUser.getEmail(),
                singupUser.getPhoneNumber()
        );

        Set<String> strRole = singupUser.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRole == null) {
            Role role = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(role);
        } else {
            strRole.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                    case "user":
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        userModel.setRoles(roles);
        userRepository.save(userModel);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principle.toString() != "anonymousUser") {
            Integer userId = Math.toIntExact(((UserDetailsImpl) principle).getId());
        }
        return ResponseEntity.ok()
                .body(new MessageResponse("You've been signed out!"));
    }
}
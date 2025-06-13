package com.example.springsecurityjwt.controllers;

import com.example.springsecurityjwt.enums.PermissionsEnum;
import com.example.springsecurityjwt.enums.RoleEnum;
import com.example.springsecurityjwt.exceptions.SecurityErrorHandler;
import com.example.springsecurityjwt.models.PermissionEntity;
import com.example.springsecurityjwt.models.RoleEntity;
import com.example.springsecurityjwt.models.UserEntity;
import com.example.springsecurityjwt.repositories.UserRepository;
import com.example.springsecurityjwt.requests.UserRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authorization.method.HandleAuthorizationDenied;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@HandleAuthorizationDenied(handlerClass = SecurityErrorHandler.class)
@Slf4j
public class PrincipalController {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public PrincipalController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/health-check")
    //@PreAuthorize("hasAuthority('READ')")
    public String hello() {
        log.info("HEALTH CHECK HELLO");
        return "Health check not secured";
    }

    @GetMapping("/health-check-secured")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String helloSecured() {
        log.info("HEALTH CHECK HELLO SECURED");
        return "Health check secured";
    }

    @PostMapping("/user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> saveUser(@Valid @RequestBody UserRequest userRequest) {

        Set<RoleEntity> roles = userRequest.getRoles().stream()
                .map(role -> RoleEntity.builder()
                        .name(RoleEnum.valueOf(role.getRole().name()))
                        .permissions(role.getPermissions().stream()
                                .map(permission -> PermissionEntity.builder()
                                        .name(PermissionsEnum.valueOf(permission.getPermission().name())).build()
                                ).collect(Collectors.toSet()))
                        .build())
                .collect(Collectors.toSet());

        UserEntity userEntity = UserEntity.builder()
                .email(userRequest.getEmail())
                .username(userRequest.getUsername())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .roles(roles).build();

        userRepository.save(userEntity);

        return ResponseEntity.ok(userEntity);
    }

    @DeleteMapping("/delete-user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@RequestParam String id) {

        userRepository.deleteById(Long.parseLong(id));

        return ResponseEntity.ok(new HashMap<String, String>().put("message", "Usuario eliminado: " + id));
    }
}

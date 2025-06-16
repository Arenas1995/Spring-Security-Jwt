package com.example.springsecurityjwt.controllers;

import com.example.springsecurityjwt.exceptions.SecurityErrorHandler;
import com.example.springsecurityjwt.models.entities.UserEntity;
import com.example.springsecurityjwt.repositories.UserRepository;
import com.example.springsecurityjwt.requests.UserRequest;
import com.example.springsecurityjwt.responses.UserResponse;
import com.example.springsecurityjwt.services.ports.UserDetailService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authorization.method.HandleAuthorizationDenied;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/user")
@HandleAuthorizationDenied(handlerClass = SecurityErrorHandler.class)
@Slf4j
public class UserController {

    private final UserDetailService userDetailService;
    private final UserRepository userRepository;

    public UserController(UserDetailService userDetailService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userDetailService = userDetailService;
        this.userRepository = userRepository;
    }

    @GetMapping("/find-all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> findAllUsers() {
        List<UserEntity> userEntityList = (List<UserEntity>) userRepository.findAll();
        return ResponseEntity.ok(userEntityList);
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> saveUser(@Valid @RequestBody UserRequest userRequest) {
        UserResponse userResponse = userDetailService.saveUser(userRequest);
        return ResponseEntity.ok(userResponse);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@RequestParam UUID id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok(new HashMap<String, String>().put("message", "Usuario eliminado: " + id));
    }
}

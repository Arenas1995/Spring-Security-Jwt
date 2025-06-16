package com.example.springsecurityjwt.controllers;

import com.example.springsecurityjwt.exceptions.SecurityErrorHandler;
import com.example.springsecurityjwt.requests.RoleRequest;
import com.example.springsecurityjwt.responses.RoleResponse;
import com.example.springsecurityjwt.services.ports.RoleService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.method.HandleAuthorizationDenied;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/ex/role")
@HandleAuthorizationDenied(handlerClass = SecurityErrorHandler.class)
@Slf4j
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/find-all")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> findAllRoles() {
        return ResponseEntity.ok(roleService.findAllRoles());
    }

    @PostMapping("/save")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> saveRole(@Valid @RequestBody RoleRequest roleRequest) {
        RoleResponse roleResponse = roleService.saveRole(roleRequest);
        return ResponseEntity.ok(roleResponse);
    }
}

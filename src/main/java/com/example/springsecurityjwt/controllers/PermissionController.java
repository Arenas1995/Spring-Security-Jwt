package com.example.springsecurityjwt.controllers;

import com.example.springsecurityjwt.exceptions.SecurityErrorHandler;
import com.example.springsecurityjwt.requests.PermissionRequest;
import com.example.springsecurityjwt.responses.PermissionResponse;
import com.example.springsecurityjwt.services.ports.PermissionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authorization.method.HandleAuthorizationDenied;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/ex/permission")
@HandleAuthorizationDenied(handlerClass = SecurityErrorHandler.class)
@Slf4j
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping("/find-all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> findAllPermissions() {
        return ResponseEntity.ok(permissionService.findAllPermissions());
    }

    @PostMapping("/save")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> savePermission(@Valid @RequestBody List<PermissionRequest> permissionRequest) {
        List<PermissionResponse> permissionResponseList = permissionService.savePermissions(permissionRequest);
        return ResponseEntity.ok(permissionResponseList);
    }
}

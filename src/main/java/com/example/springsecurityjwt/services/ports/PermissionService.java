package com.example.springsecurityjwt.services.ports;

import com.example.springsecurityjwt.requests.PermissionRequest;
import com.example.springsecurityjwt.responses.PermissionResponse;

import java.util.List;

public interface PermissionService {

    List<PermissionResponse> findAllPermissions();

    List<PermissionResponse> savePermissions(List<PermissionRequest> permissionRequest);
}

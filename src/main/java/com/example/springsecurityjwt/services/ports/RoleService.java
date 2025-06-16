package com.example.springsecurityjwt.services.ports;

import com.example.springsecurityjwt.requests.RoleRequest;
import com.example.springsecurityjwt.responses.RoleResponse;

import java.util.List;

public interface RoleService {

    List<RoleResponse> findAllRoles();

    RoleResponse saveRole(RoleRequest roleRequest);
}

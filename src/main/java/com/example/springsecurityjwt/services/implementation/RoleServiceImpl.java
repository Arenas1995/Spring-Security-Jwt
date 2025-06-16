package com.example.springsecurityjwt.services.implementation;

import com.example.springsecurityjwt.mappers.RoleMapper;
import com.example.springsecurityjwt.models.entities.PermissionEntity;
import com.example.springsecurityjwt.models.entities.RoleEntity;
import com.example.springsecurityjwt.repositories.PermissionRepository;
import com.example.springsecurityjwt.repositories.RoleRepository;
import com.example.springsecurityjwt.requests.RoleRequest;
import com.example.springsecurityjwt.responses.RoleResponse;
import com.example.springsecurityjwt.services.ports.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private final PermissionRepository permissionRepository;

    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleRepository roleRepository, PermissionRepository permissionRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public List<RoleResponse> findAllRoles() {
        List<RoleEntity> roleEntityList = roleRepository.findAll();
        return roleMapper.toResponseList(roleEntityList);
    }

    @Override
    public RoleResponse saveRole(RoleRequest roleRequest) {

        Set<PermissionEntity> permissionEntitySet = roleRequest.getPermissions().stream()
                .map(p -> permissionRepository.findByName(p.getName())
                        .orElseThrow(() -> new RuntimeException("Permiso no encontrado: " + p.getName())))
                .collect(Collectors.toSet());

        RoleEntity roleEntity = roleMapper.toEntity(roleRequest);
        roleEntity.setPermissions(permissionEntitySet);

        return roleMapper.toResponse(roleRepository.save(roleEntity));
    }
}

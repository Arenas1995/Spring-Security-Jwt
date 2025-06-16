package com.example.springsecurityjwt.services.implementation;

import com.example.springsecurityjwt.mappers.PermissionMapper;
import com.example.springsecurityjwt.models.entities.PermissionEntity;
import com.example.springsecurityjwt.repositories.PermissionRepository;
import com.example.springsecurityjwt.requests.PermissionRequest;
import com.example.springsecurityjwt.responses.PermissionResponse;
import com.example.springsecurityjwt.services.ports.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    private final PermissionMapper permissionMapper;

    public PermissionServiceImpl(PermissionRepository permissionRepository, PermissionMapper permissionMapper) {
        this.permissionRepository = permissionRepository;
        this.permissionMapper = permissionMapper;
    }

    @Override
    public List<PermissionResponse> findAllPermissions() {
        List<PermissionEntity> permissionEntityList = permissionRepository.findAll();
        return permissionMapper.toResponseList(permissionEntityList);
    }

    @Override
    public List<PermissionResponse> savePermissions(List<PermissionRequest> permissionRequests) {

        List<PermissionEntity> entities = permissionRequests.stream()
                .map(request -> {
                    PermissionEntity entity = permissionMapper.toEntity(request);
                    entity.setCreateDate(LocalDateTime.now());
                    entity.setUpdateDate(LocalDateTime.now());
                    return entity;
                }).collect(Collectors.toList());

        List<PermissionEntity> savedPermissions = permissionRepository.saveAll(entities);

        return savedPermissions.stream()
                .map(permissionMapper::toResponse)
                .collect(Collectors.toList());
    }
}

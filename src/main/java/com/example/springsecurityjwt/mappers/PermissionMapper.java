package com.example.springsecurityjwt.mappers;

import com.example.springsecurityjwt.models.entities.PermissionEntity;
import com.example.springsecurityjwt.requests.PermissionRequest;
import com.example.springsecurityjwt.responses.PermissionResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    PermissionEntity toEntity(PermissionRequest permissionRequest);

    PermissionResponse toResponse(PermissionEntity permissionEntity);

    List<PermissionResponse> toResponseList(List<PermissionEntity> permissionEntityList);
}

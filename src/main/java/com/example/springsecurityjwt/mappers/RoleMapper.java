package com.example.springsecurityjwt.mappers;

import com.example.springsecurityjwt.models.entities.RoleEntity;
import com.example.springsecurityjwt.requests.RoleRequest;
import com.example.springsecurityjwt.responses.RoleResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleEntity toEntity(RoleRequest roleRequest);

    RoleResponse toResponse(RoleEntity roleEntity);

    List<RoleResponse> toResponseList(List<RoleEntity> roleEntityList);
}

package com.example.springsecurityjwt.mappers;

import com.example.springsecurityjwt.models.entities.UserEntity;
import com.example.springsecurityjwt.responses.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toResponse(UserEntity userEntity);

    UserEntity toEntity(UserResponse userResponse);
}

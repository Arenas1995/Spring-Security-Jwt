package com.example.springsecurityjwt.responses;

import com.example.springsecurityjwt.enums.RoleEnum;
import com.example.springsecurityjwt.models.entities.PermissionEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class RoleResponse extends CommonsResponse {

    private UUID id;

    private RoleEnum name;

    private Set<PermissionEntity> permissions;

}

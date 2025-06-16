package com.example.springsecurityjwt.responses;

import com.example.springsecurityjwt.models.entities.RoleEntity;
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
public class UserResponse extends CommonsResponse {

    private UUID id;

    private String email;

    private String username;

    private String password;

    private boolean enabled;

    private boolean accountNoExpired;

    private boolean accountNoLocked;

    private boolean credentialNoExpired;

    private Set<RoleEntity> roles;
}

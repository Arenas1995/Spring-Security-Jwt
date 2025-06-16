package com.example.springsecurityjwt.requests;

import com.example.springsecurityjwt.enums.RoleEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleRequest {

    @NotNull
    private RoleEnum name;

    @NotEmpty
    private Set<PermissionRequest> permissions;
}

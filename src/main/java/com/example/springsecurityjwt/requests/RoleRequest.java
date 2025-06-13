package com.example.springsecurityjwt.requests;

import com.example.springsecurityjwt.enums.RoleEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleRequest {

    @NotBlank
    private RoleEnum role;

    @NotEmpty
    private Set<PermissionRequest> permissions;
}

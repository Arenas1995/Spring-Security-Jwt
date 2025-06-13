package com.example.springsecurityjwt.requests;

import com.example.springsecurityjwt.enums.RoleEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleRequest {

    @NotBlank
    private RoleEnum role;

    @NotEmpty
    private Set<PermissionRequest> permissions;
}

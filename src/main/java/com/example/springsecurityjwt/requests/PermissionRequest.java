package com.example.springsecurityjwt.requests;

import com.example.springsecurityjwt.enums.PermissionsEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PermissionRequest {

    @NotBlank
    private PermissionsEnum permission;

}

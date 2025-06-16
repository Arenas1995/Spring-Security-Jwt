package com.example.springsecurityjwt.requests;

import com.example.springsecurityjwt.enums.PermissionsEnum;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PermissionRequest {

    @NotNull
    private PermissionsEnum name;

}

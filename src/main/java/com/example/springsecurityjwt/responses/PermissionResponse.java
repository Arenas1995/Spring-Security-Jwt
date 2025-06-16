package com.example.springsecurityjwt.responses;

import com.example.springsecurityjwt.enums.PermissionsEnum;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PermissionResponse extends CommonsResponse {

    private UUID id;

    private PermissionsEnum name;

}

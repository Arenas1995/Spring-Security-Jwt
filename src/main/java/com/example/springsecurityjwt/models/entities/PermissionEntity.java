package com.example.springsecurityjwt.models.entities;

import com.example.springsecurityjwt.enums.PermissionsEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "permissions")
public class PermissionEntity extends CommonsEntity {

    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.BINARY)
    @Column(name = "id", columnDefinition = "BINARY(16)", nullable = false, updatable = false)
    private UUID id;

    @Column(unique = true, nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private PermissionsEnum name;

}

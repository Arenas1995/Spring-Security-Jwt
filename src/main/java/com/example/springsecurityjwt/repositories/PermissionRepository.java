package com.example.springsecurityjwt.repositories;

import com.example.springsecurityjwt.enums.PermissionsEnum;
import com.example.springsecurityjwt.models.entities.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionEntity, UUID> {

    Optional<PermissionEntity> findByName(PermissionsEnum permissionsName);
}

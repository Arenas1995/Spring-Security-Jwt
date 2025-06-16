package com.example.springsecurityjwt.repositories;

import com.example.springsecurityjwt.enums.RoleEnum;
import com.example.springsecurityjwt.models.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, UUID> {

    List<RoleEntity> findRoleEntitiesByNameIn(List<String> roleNames);

    Optional<RoleEntity> findByName(RoleEnum roleName);
}

package com.example.springsecurityjwt.services;

import com.example.springsecurityjwt.jwt.JwtUtilities;
import com.example.springsecurityjwt.mappers.UserMapper;
import com.example.springsecurityjwt.models.entities.RoleEntity;
import com.example.springsecurityjwt.models.entities.UserEntity;
import com.example.springsecurityjwt.repositories.PermissionRepository;
import com.example.springsecurityjwt.repositories.RoleRepository;
import com.example.springsecurityjwt.repositories.UserRepository;
import com.example.springsecurityjwt.requests.AuthCreateUserRequest;
import com.example.springsecurityjwt.requests.UserRequest;
import com.example.springsecurityjwt.responses.AuthResponse;
import com.example.springsecurityjwt.responses.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService, UserDetailService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PermissionRepository permissionRepository;

    private final JwtUtilities jwtUtilities;

    private final PasswordEncoder passwordEncoder;

    private final MessageSource messageSource;

    private final UserMapper userMapper;

    public UserDetailsServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PermissionRepository permissionRepository, JwtUtilities jwtUtilities, PasswordEncoder passwordEncoder, MessageSource messageSource, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.jwtUtilities = jwtUtilities;
        this.passwordEncoder = passwordEncoder;
        this.messageSource = messageSource;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("INICIA PROCESO CARGAR USUARIO");
        UserEntity userEntity = userRepository.findByUsername(username).orElse(null);

        if (userEntity == null) {
            return null;
        }

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        userEntity.getRoles().forEach(role ->
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName().name())));

        userEntity.getRoles().stream().flatMap(role -> role.getPermissions().stream())
                .forEach(permission ->
                        authorities.add(new SimpleGrantedAuthority(permission.getName().name())));

        return new User(userEntity.getUsername(), userEntity.getPassword(),
                userEntity.isEnabled(), userEntity.isAccountNoExpired(), userEntity.isCredentialNoExpired(),
                userEntity.isAccountNoLocked(), authorities);
    }

    @Override
    public AuthResponse registerUser(AuthCreateUserRequest authCreateUserRequest) {

        log.info("INICIA PROCESO REGISTRAR USUARIO");
        String username = authCreateUserRequest.username();
        String password = authCreateUserRequest.password();
        List<String> rolesRequest = authCreateUserRequest.roleRequest().roleListName();

        Set<RoleEntity> roleEntityList = new HashSet<>(roleRepository.findRoleEntitiesByNameIn(rolesRequest));

        if (roleEntityList.isEmpty()) {
            log.error("ROLES NO ENCONTRADOS");
            throw new IllegalArgumentException("The roles specified does not exist.");
        }

        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(username)
                .roles(roleEntityList)
                .enabled(true)
                .accountNoLocked(true)
                .accountNoExpired(true)
                .credentialNoExpired(true)
                .createUser(username)
                .updateUser(username).build();

        UserEntity userSaved = userRepository.save(userEntity);

        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();

        userSaved.getRoles().forEach(role ->
                authorities.add(new SimpleGrantedAuthority("ROLE_".concat(role.getName().name()))));

        userSaved.getRoles().stream().flatMap(role ->
                role.getPermissions().stream()).forEach(permission ->
                authorities.add(new SimpleGrantedAuthority(permission.getName().name())));

        SecurityContext securityContextHolder = SecurityContextHolder.getContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken(userSaved, null, authorities);

        String accessToken = jwtUtilities.generateAccesToken(authentication);

        log.info("USUARIO CREADO CON EXITO");
        return new AuthResponse(username, "User created successfully", accessToken, true);
    }

    @Override
    public UserResponse saveUser(UserRequest userRequest) {
        // Obtener los roles desde la base de datos
        Set<RoleEntity> roles = userRequest.getRoles().stream()
                .map(roleRequest -> roleRepository.findByName(roleRequest.getRole())
                        .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + roleRequest.getRole())))
                .collect(Collectors.toSet());

        // Crear usuario
        UserEntity userEntity = UserEntity.builder()
                .email(userRequest.getEmail())
                .username(userRequest.getUsername())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .roles(roles)
                .enabled(true)
                .accountNoLocked(true)
                .accountNoExpired(true)
                .credentialNoExpired(true)
                .createUser(userRequest.getUsername())
                .updateUser(userRequest.getUsername())
                .build();

        // Guardar usuario y retornar respuesta
        return userMapper.toResponse(userRepository.save(userEntity));
    }

}

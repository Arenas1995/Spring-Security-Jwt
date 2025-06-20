package com.example.springsecurityjwt.filters;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.springsecurityjwt.jwt.JwtUtilities;
import com.example.springsecurityjwt.jwt.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

@Component
@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    private final JwtUtilities jwtUtilities;

    //private final UserDetailsService userDetailsService;

    public JwtAuthorizationFilter(JwtUtils jwtUtils, JwtUtilities jwtUtilities) {
        this.jwtUtils = jwtUtils;
        this.jwtUtilities = jwtUtilities;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        log.info("INICIA PROCESO FILTRO DE SEGURIDAD");

        try {
            String path = request.getServletPath();
            String method = request.getMethod();

            // Evita filtrar rutas públicas
            if (path.startsWith("/ex/")) {
                log.info("Ruta pública detectada: {}, {}", path, method);
                filterChain.doFilter(request, response);
                return;
            }

            String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);

            if (jwtToken != null && jwtToken.startsWith("Bearer ")) {
                jwtToken = jwtToken.substring(7);
                DecodedJWT decodedJWT = jwtUtilities.validateToken(jwtToken);

                String username = jwtUtilities.extractUsername(decodedJWT);
                String stringAuthorities = jwtUtilities.getSpecificClaim(decodedJWT, "authorities").asString();

                Collection<? extends GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(stringAuthorities);

                SecurityContext context = SecurityContextHolder.createEmptyContext();
                Authentication authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
                context.setAuthentication(authenticationToken);
                SecurityContextHolder.setContext(context);

                log.info("Autenticación establecida para el usuario {}", username);
            }
            filterChain.doFilter(request, response);
            log.info("Solicitud procesada exitosamente");
        } catch (Exception ex) {
            log.error("ERROR EN EL FILTRO DE SEGURIDAD: {}", ex.getMessage(), ex);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Token inválido o expirado: " + ex.getMessage() + "\"}");
        }
    }

    /*@Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtils.extractUsername(jwt);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtUtils.validateToken(jwt, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }*/

}

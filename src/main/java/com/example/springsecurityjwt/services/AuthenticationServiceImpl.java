package com.example.springsecurityjwt.services;

import com.example.springsecurityjwt.jwt.JwtUtilities;
import com.example.springsecurityjwt.requests.AuthLoginRequest;
import com.example.springsecurityjwt.responses.AuthResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtUtilities jwtUtilities;

    private final PasswordEncoder passwordEncoder;

    private final UserDetailsService userDetailsService;

    private final MessageSource messageSource;

    public AuthenticationServiceImpl(JwtUtilities jwtUtilities, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService, MessageSource messageSource) {
        this.jwtUtilities = jwtUtilities;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.messageSource = messageSource;
    }

    @Override
    public AuthResponse loginUser(AuthLoginRequest authLoginRequest) {

        String username = authLoginRequest.username();
        String password = authLoginRequest.password();

        Authentication authentication = this.authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtilities.generateAccesToken(authentication);
        return new AuthResponse(username, "User loged succesfully", accessToken, true);
    }

    public Authentication authenticate(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (userDetails == null) {
            log.error(messageSource.getMessage("auth.user.notfound",
                    new Object[]{username}, Locale.getDefault()));
            throw new BadCredentialsException(messageSource.getMessage("auth.credentials.invalid.message",
                    null, Locale.getDefault()));
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            log.error(messageSource.getMessage("auth.password.invalid",
                    null, Locale.getDefault()));
            throw new BadCredentialsException(messageSource.getMessage("auth.credentials.invalid.message",
                    null, Locale.getDefault()));
        }

        return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
    }
}

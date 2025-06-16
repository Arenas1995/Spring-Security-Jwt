package com.example.springsecurityjwt.services.ports;

import com.example.springsecurityjwt.requests.AuthLoginRequest;
import com.example.springsecurityjwt.responses.AuthResponse;

public interface AuthenticationService {

    AuthResponse loginUser(AuthLoginRequest authLoginRequest);
}

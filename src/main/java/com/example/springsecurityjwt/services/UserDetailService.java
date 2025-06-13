package com.example.springsecurityjwt.services;

import com.example.springsecurityjwt.requests.AuthCreateUserRequest;
import com.example.springsecurityjwt.responses.AuthResponse;

public interface UserDetailService {

    AuthResponse registerUser(AuthCreateUserRequest authCreateUserRequest);
}

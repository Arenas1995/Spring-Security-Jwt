package com.example.springsecurityjwt.services.ports;

import com.example.springsecurityjwt.requests.AuthCreateUserRequest;
import com.example.springsecurityjwt.requests.UserRequest;
import com.example.springsecurityjwt.responses.AuthResponse;
import com.example.springsecurityjwt.responses.UserResponse;

public interface UserDetailService {

    AuthResponse registerUser(AuthCreateUserRequest authCreateUserRequest);

    UserResponse saveUser(UserRequest userRequest);
}

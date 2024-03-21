package com.api.greenatom.security.service;

import com.api.greenatom.security.dto.JwtAuthenticationResponse;
import com.api.greenatom.security.dto.SignInRequest;
import com.api.greenatom.security.dto.SignUpRequest;

public interface AuthenticationService {
    JwtAuthenticationResponse signUp(SignUpRequest request);
    JwtAuthenticationResponse signIn(SignInRequest request);
}


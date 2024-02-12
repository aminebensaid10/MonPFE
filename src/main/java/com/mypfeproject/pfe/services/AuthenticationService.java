package com.mypfeproject.pfe.services;

import com.mypfeproject.pfe.dto.JwtAuthenticationResponse;
import com.mypfeproject.pfe.dto.RefreshTokenRequest;
import com.mypfeproject.pfe.dto.SignUpRequest;
import com.mypfeproject.pfe.dto.SigninRequest;
import com.mypfeproject.pfe.entities.Role;
import com.mypfeproject.pfe.entities.User;

public interface AuthenticationService {
    User signup(SignUpRequest signUpRequest);
    JwtAuthenticationResponse signin(SigninRequest signinRequest) ;
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) ;
}

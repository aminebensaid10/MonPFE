package com.mypfeproject.pfe.services;

import com.mypfeproject.pfe.dto.*;
import com.mypfeproject.pfe.entities.Role;
import com.mypfeproject.pfe.entities.User;

public interface AuthenticationService {
    User signup(SignUpRequest signUpRequest);
    JwtAuthenticationResponse signin(SigninRequest signinRequest) ;
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) ;
    public User getAuthenticatedUser(String userEmail) ;
    User updateProfile(String userEmail, SignUpRequest signUpRequest);
    void changePassword(String userEmail, ChangePasswordRequest changePasswordRequest);
}

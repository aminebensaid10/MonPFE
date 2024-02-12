package com.mypfeproject.pfe.controller;

import com.mypfeproject.pfe.dto.JwtAuthenticationResponse;
import com.mypfeproject.pfe.dto.RefreshTokenRequest;
import com.mypfeproject.pfe.dto.SignUpRequest;
import com.mypfeproject.pfe.dto.SigninRequest;
import com.mypfeproject.pfe.entities.Role;
import com.mypfeproject.pfe.entities.User;
import com.mypfeproject.pfe.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AutheticationController {
private final AuthenticationService authenticationService ;
    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestParam("nom") String nom,
                                       @RequestParam("prenom") String prenom,
                                       @RequestParam("email") String email,
                                       @RequestParam("password") String password,
                                       @RequestParam("role") Role role,
                                       @RequestPart("image") MultipartFile image) {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setNom(nom);
        signUpRequest.setPrenom(prenom);
        signUpRequest.setEmail(email);
        signUpRequest.setPassword(password);
        signUpRequest.setRole(role);
        signUpRequest.setImage(image);

        return ResponseEntity.ok(authenticationService.signup(signUpRequest));
    }


@PostMapping("/signin")
public ResponseEntity<JwtAuthenticationResponse>signin(@RequestBody SigninRequest signinRequest){
        return ResponseEntity.ok(authenticationService.signin(signinRequest));
    }
    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse>refresh(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }
}

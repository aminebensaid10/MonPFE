package com.mypfeproject.pfe.controller;

import com.mypfeproject.pfe.dto.*;
import com.mypfeproject.pfe.entities.Role;
import com.mypfeproject.pfe.entities.User;
import com.mypfeproject.pfe.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

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
                                       @RequestParam("numeroTelephone") String numeroTelephone,
                                   @RequestParam("dateNaissance") String dateNaissance,


                                       @RequestParam("role") Role role,
                                       @RequestPart("image") MultipartFile image) {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setNom(nom);
        signUpRequest.setPrenom(prenom);
        signUpRequest.setEmail(email);
        signUpRequest.setNumeroTelephone(numeroTelephone);
        signUpRequest.setDateNaissance(dateNaissance);

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
    @GetMapping("/profile")
    public ResponseEntity<User> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            String userEmail = userDetails.getUsername();
            User authenticatedUser = authenticationService.getAuthenticatedUser(userEmail);
            return ResponseEntity.ok(authenticatedUser);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();

        authenticationService.changePassword(userEmail, changePasswordRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }



}

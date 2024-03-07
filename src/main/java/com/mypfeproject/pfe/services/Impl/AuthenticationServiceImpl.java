package com.mypfeproject.pfe.services.Impl;

import com.mypfeproject.pfe.dto.JwtAuthenticationResponse;
import com.mypfeproject.pfe.dto.RefreshTokenRequest;
import com.mypfeproject.pfe.dto.SignUpRequest;
import com.mypfeproject.pfe.dto.SigninRequest;
import com.mypfeproject.pfe.entities.Role;
import com.mypfeproject.pfe.entities.User;
import com.mypfeproject.pfe.repository.UserRepository;
import com.mypfeproject.pfe.services.AuthenticationService;
import com.mypfeproject.pfe.services.JWTService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
private final UserRepository userRepository ;
private final PasswordEncoder passwordEncoder;
private final AuthenticationManager autheticationManager ;
private final JWTService jwtService ;
public User signup(SignUpRequest signUpRequest){
   User user =new User();
   user.setEmail(signUpRequest.getEmail());
   user.setNom(signUpRequest.getNom());
    user.setPrenom(signUpRequest.getPrenom());
    user.setNumeroTelephone(signUpRequest.getNumeroTelephone());
    user.setDateNaissance(signUpRequest.getDateNaissance());
    user.setRole(signUpRequest.getRole());
    user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
    user.setImagePath(saveImage(signUpRequest.getImage()));
    return userRepository.save(user);




}
    private String saveImage(MultipartFile image) {
        String imageName = UUID.randomUUID() + image.getOriginalFilename();
        String imagePath = "fichiers/" + imageName;
        File imageFile = new File(imagePath);

        try {
            FileUtils.writeByteArrayToFile(imageFile, image.getBytes());
            return imageName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public JwtAuthenticationResponse signin(SigninRequest signinRequest){
autheticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getEmail(),signinRequest.getPassword()));
    var user = userRepository.findByEmail(signinRequest.getEmail()).orElseThrow(()->new IllegalArgumentException("invalid email or password."));
    var jwt =jwtService.generateToken(user);
    var refreshtoken =jwtService.generateRefreshToken(new HashMap<>(),user);
JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
jwtAuthenticationResponse.setToken(jwt);
jwtAuthenticationResponse.setRefreshtoken(refreshtoken);
return jwtAuthenticationResponse;
}
public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
    String userEmail=jwtService.extractUserName(refreshTokenRequest.getToken());
    User user=userRepository.findByEmail(userEmail).orElseThrow();
   if(jwtService.isTokenValid((refreshTokenRequest.getToken()), user)){
    var jwt = jwtService.generateToken(user);
       JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
       jwtAuthenticationResponse.setToken(jwt);
       jwtAuthenticationResponse.setRefreshtoken(refreshTokenRequest.getToken());
       return jwtAuthenticationResponse;
   }
   return null ;
}
    public User getAuthenticatedUser(String userEmail) {
        return userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé"));
    }

}

package com.mypfeproject.pfe.services;

import com.mypfeproject.pfe.entities.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public interface JWTService {
String extractUserName(String token);
public String generateToken(UserDetails userDetails) ;
boolean isTokenValid(String token, UserDetails userDetails);

String generateRefreshToken(Map<String, Objects> extraclaims, UserDetails userDetails);
}

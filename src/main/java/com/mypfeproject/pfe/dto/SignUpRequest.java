package com.mypfeproject.pfe.dto;

import com.mypfeproject.pfe.entities.Role;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class SignUpRequest {
    private String nom ;
    private String prenom ;
    private String email ;
    private String password ;
    private String numeroTelephone;
    private String dateNaissance;

    private Role role;
    private MultipartFile image;

}

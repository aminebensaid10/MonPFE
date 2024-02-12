package com.mypfeproject.pfe.dto;

import com.mypfeproject.pfe.entities.Role;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SignUpRequest {
    private String nom ;
    private String prenom ;
    private String email ;
    private String password ;
    private Role role;
    private MultipartFile image;

}

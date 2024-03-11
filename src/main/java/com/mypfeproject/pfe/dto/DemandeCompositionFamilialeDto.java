package com.mypfeproject.pfe.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mypfeproject.pfe.entities.User;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class DemandeCompositionFamilialeDto {
    private String nomMembre;
    private String prenomMembre;
    private String sexe;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateNaissance;
    private String lienParente;
    @JsonIgnore
    private MultipartFile imageMembre;

    private MultipartFile justificatifFile;

    private String commentaire;
    private boolean valide  ;
    private String isUpdated = "Pas encore";

    @JsonIgnore
    private User collaborateur;
}

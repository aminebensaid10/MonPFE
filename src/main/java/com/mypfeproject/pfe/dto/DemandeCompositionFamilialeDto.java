package com.mypfeproject.pfe.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mypfeproject.pfe.entities.User;
import lombok.Data;

import java.util.Date;

@Data
public class DemandeCompositionFamilialeDto {
    private String nomMembre;
    private String prenomMembre;
    private String sexe;
    private Date dateNaissance;
    private String lienParente;
    private String justificatif;
    private String commentaire;
    private boolean valide  ;
    private String isUpdated = "Pas encore";

    @JsonIgnore
    private User collaborateur;
}

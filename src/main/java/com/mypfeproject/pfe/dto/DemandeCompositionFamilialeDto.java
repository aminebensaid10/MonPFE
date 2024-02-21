package com.mypfeproject.pfe.dto;

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
}

package com.mypfeproject.pfe.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Data
@Setter
@Entity
@Table(name = "membre_famille")
public class MembreFamille {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nomMembre")
    private String nomMembre;

    @Column(name = "prenomMembre")
    private String prenomMembre;
    @Column(name = "sexe")
    private String sexe;

    @Column(name = "dateNaissance")
    private Date dateNaissance;

    @Column(name = "lienParente")
    private String lienParente;

    @Column(name = "justificatif")
    private String justificatif;

    @Column(name = "commentaire")
    private String commentaire;
}

package com.mypfeproject.pfe.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

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


//   // private byte[] justificatif;

    @Column(name = "justificatif_path")
    private String justificatifPath;


    @Column(name = "image_path")
    private String imagePath;
    @Column(name = "commentaire")
    private String commentaire;
    @Column(name = "valide")
    private boolean valide = false ;
    @Column(name = "is_updated")
    private String isUpdated = "Pas encore";

    @ManyToOne
    @JoinColumn(name = "collaborateur_id")
    private User collaborateur;


}

package com.mypfeproject.pfe.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "demande_demenagement")



public class DemandeDemenagement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "collaborateur_id")
    private User collaborateur;
    @Column(name = "justificatif_path")
    private String justificatifPath;
    @Column(name = "nouvelle_adresse")
    private String nouvelleAdresse;
    @Column(name = "etat")
    private String etat;
    @Column(name = "type_demande")
    private String typeDemande;
    @OneToMany(mappedBy = "demandeDemenagement", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Notification> notifications;
}

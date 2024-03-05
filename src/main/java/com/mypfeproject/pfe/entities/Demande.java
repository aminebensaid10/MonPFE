package com.mypfeproject.pfe.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "demande_composition_familiale")
public class Demande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "membre_famille_id")
    private MembreFamille membreFamille;

    @ManyToOne
    @JoinColumn(name = "collaborateur_id")
    private User collaborateur;

    @Column(name = "etat")
    private String etat;

    @Column(name = "type_demande")
    private String typeDemande;
    @OneToMany(mappedBy = "demande", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Notification> notifications;

}

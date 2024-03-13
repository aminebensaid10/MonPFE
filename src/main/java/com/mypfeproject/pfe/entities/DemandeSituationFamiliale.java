package com.mypfeproject.pfe.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "demande_situation_familiale")
public class DemandeSituationFamiliale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "collaborateur_id")
    private User collaborateur;

    @Enumerated(EnumType.STRING)
    private SituationFamiliale nouvelleSituation;

    @Column(name = "justificatif_path")
    private String justificatifPath;

    @Column(name = "etat")
    private String etat;
    @Column(name = "type_demande")
    private String typeDemande;
    @OneToMany(mappedBy = "demandesituationfamiliale", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Notification> notifications;

}

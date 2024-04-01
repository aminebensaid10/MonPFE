package com.mypfeproject.pfe.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "mode_transport")
public class DemandeModeTransport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type_transport")
    private String typeTransport;
    @ManyToOne
    @JoinColumn(name = "collaborateur_id")
    private User collaborateur;
    @Column(name = "justificatif_path")
    private String justificatifPath;
    @Column(name = "etat")
    private String etat;
    @Column(name = "type_demande")
    private String typeDemande;
    @OneToMany(mappedBy = "demandeModeTransport", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Notification> notifications;
}

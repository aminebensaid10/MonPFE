package com.mypfeproject.pfe.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
@Table(name = "notifications")


public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "collaborateur_id")
    private User collaborateur;

    @Column(name = "message")
    private String message;

    @Column(name = "is_read")
    private boolean isRead;
    @ManyToOne
    @JoinColumn(name = "demande_id")
    private Demande demande;
    @ManyToOne
    @JoinColumn(name = "demandesituationfamiliale_id")
    private DemandeSituationFamiliale demandesituationfamiliale;

}

package com.mypfeproject.pfe.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
@Getter
@Setter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom",nullable = false)
    private String nom;

    @Column(name = "prenom", nullable = false)
    private String prenom;

    @Column(name = "email", unique = true, nullable = false)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "date_naissance")
    private String dateNaissance;

    @Column(name = "numero_telephone")
    private String numeroTelephone;

    @Column(name = "adresse_principale")
    private String adressePrincipale;

    @Enumerated(EnumType.STRING)
    private SituationFamiliale situationFamiliale;


    @Column(name = "mode_de_transport")
    private String modeDeTransport;
    private Role role ;
    @Column(name = "image_path")
    private String imagePath;
    @OneToMany(mappedBy = "collaborateur", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<DemandeSituationFamiliale> demandesSituationFamiliale;
    @Column(name = "demande_validée_situation_familiale")
    private boolean demandeValidee;
    @Column(name = "demande_validée_déménagemenet")
    private boolean demandeValideeDemenagment;
    @Column(name = "demande_validée_mode_transport")
    private boolean demandeValideeModeTransport;




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

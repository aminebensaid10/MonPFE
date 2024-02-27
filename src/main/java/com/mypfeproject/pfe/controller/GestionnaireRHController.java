package com.mypfeproject.pfe.controller;

import com.mypfeproject.pfe.entities.Demande;
import com.mypfeproject.pfe.services.GestionnaireRhService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/gestionnaireRH")
@RequiredArgsConstructor
public class GestionnaireRHController {
    @Autowired
    private final GestionnaireRhService gestionnaireRhService;

    @GetMapping
    public ResponseEntity<String>sayHello(){
        return ResponseEntity.ok("Hi Gestionnaire RH");
    }




    @PostMapping("/valider-demande/{demandeId}")
    @PreAuthorize("hasAnyAuthority('GESTIONNAIRERH')")
    public ResponseEntity<Void> validerDemande(@PathVariable Long demandeId) {
        try {
            gestionnaireRhService.validerDemande(demandeId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("/rejeter-demande/{demandeId}")
    @PreAuthorize("hasAnyAuthority('GESTIONNAIRERH')")
    public ResponseEntity<Void> rejeterDemande(@PathVariable Long demandeId) {
        try {
            gestionnaireRhService.rejeterDemande(demandeId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/demandes-groupes")
    @PreAuthorize("hasAnyAuthority('GESTIONNAIRERH')")
    public ResponseEntity<Map<Long, List<Demande>>> getDemandesGroupedByCollaborateur() {
        try {
            Map<Long, List<Demande>> demandesGrouped = gestionnaireRhService.getDemandesGroupedByCollaborateur();
            return ResponseEntity.ok(demandesGrouped);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/demandes/{id}")
    @PreAuthorize("hasAnyAuthority('GESTIONNAIRERH')")

    public ResponseEntity<Demande> getDemandeById(@PathVariable Long id) {
        Optional<Demande> demande = gestionnaireRhService.getDemandeById(id);

        return demande.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


}


package com.mypfeproject.pfe.controller;

import com.mypfeproject.pfe.entities.Demande;
import com.mypfeproject.pfe.entities.DemandeSituationFamiliale;
import com.mypfeproject.pfe.entities.Notification;
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
    @GetMapping("/demandes-situation/{id}")
    @PreAuthorize("hasAnyAuthority('GESTIONNAIRERH')")

    public ResponseEntity<DemandeSituationFamiliale> getDemandeSituationById(@PathVariable Long id) {
        Optional<DemandeSituationFamiliale> demande = gestionnaireRhService.getDemandeSituationById(id);

        return demande.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/notification")
    @PreAuthorize("hasAnyAuthority('GESTIONNAIRERH')")
    public ResponseEntity<List<Notification>> getAllNotifications() {
        try {
            List<Notification> notifications = gestionnaireRhService.getAllNotifications();
            return ResponseEntity.ok(notifications);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/unreadnotification")
    @PreAuthorize("hasAnyAuthority('GESTIONNAIRERH')")

    public ResponseEntity<List<Notification>> getAllUnreadNotifications() {
        List<Notification> unreadNotifications = gestionnaireRhService.getAllUnreadNotifications();
        return new ResponseEntity<>(unreadNotifications, HttpStatus.OK);
    }
    @GetMapping("/demandes-situation-familiale")
    @PreAuthorize("hasAnyAuthority('GESTIONNAIRERH')")
    public ResponseEntity<List<DemandeSituationFamiliale>> getAllDemandesSituationFamiliale() {
        try {
            List<DemandeSituationFamiliale> demandesSituationFamiliale = gestionnaireRhService.getAllDemandesSituationFamiliale();
            return ResponseEntity.ok(demandesSituationFamiliale);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/valider-demande-situation/{demandesituationfamiliale_id}")
    @PreAuthorize("hasAnyAuthority('GESTIONNAIRERH')")
    public ResponseEntity<Void> validerDemandesituation(@PathVariable Long demandesituationfamiliale_id) {
        try {
            gestionnaireRhService.validerDemandeSituationFamiliale(demandesituationfamiliale_id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("/rejeter-demande-situation/{demandesituationfamiliale_id}")
    @PreAuthorize("hasAnyAuthority('GESTIONNAIRERH')")
    public ResponseEntity<Void> rejeterDemandeSituation(@PathVariable Long demandesituationfamiliale_id) {
        try {
            gestionnaireRhService.rejeterDemandeSituation(demandesituationfamiliale_id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}


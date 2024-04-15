package com.mypfeproject.pfe.controller;

import com.mypfeproject.pfe.entities.*;
import com.mypfeproject.pfe.services.GestionnaireRhService;
import com.mypfeproject.pfe.services.UserService;
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
    @Autowired
    private final UserService userService;

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
    @GetMapping("/demandes-demenagement/{id}")
    @PreAuthorize("hasAnyAuthority('GESTIONNAIRERH')")

    public ResponseEntity<DemandeDemenagement> getDemandeDemenagementById(@PathVariable Long id) {
        Optional<DemandeDemenagement> demandeDemenagement = gestionnaireRhService.getDemandeDemenagementById(id);

        return demandeDemenagement.map(ResponseEntity::ok)
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
    @GetMapping("/situation-familiale")
    @PreAuthorize("hasAnyAuthority('GESTIONNAIRERH', 'GESTIONNAIREPAIE')")
    public ResponseEntity<List<User>> getCollaborateursAvecSituationFamiliale() {
        try {
            List<User> collaborateurs = gestionnaireRhService.getAllCollaborateursWithSituationFamiliale();
            return ResponseEntity.ok(collaborateurs);
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
    @PostMapping("/valider-demande-demenagement/{demandeDemenagementId}")
    @PreAuthorize("hasAnyAuthority('GESTIONNAIRERH')")
    public ResponseEntity<Void> validerDemandeDemenagement(@PathVariable Long demandeDemenagementId) {
        try {
            gestionnaireRhService.validerDemandeDemenagement(demandeDemenagementId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("/rejeter-demande-demenagement/{demandeDemenagementId}")
    @PreAuthorize("hasAnyAuthority('GESTIONNAIRERH')")
    public ResponseEntity<Void> rejeterDemandeDemenagement(@PathVariable Long demandeDemenagementId) {
        try {
            gestionnaireRhService.rejeterDemandeDemenagement(demandeDemenagementId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/demandes-demenagement")
    @PreAuthorize("hasAnyAuthority('GESTIONNAIRERH')")
    public ResponseEntity<List<DemandeDemenagement>> getAllDemandesDemenagements() {
        try {
            List<DemandeDemenagement> demandeDemenagements = gestionnaireRhService.getAllDemandesDemenagement();
            return ResponseEntity.ok(demandeDemenagements);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/membres-tous")
    @PreAuthorize("hasAnyAuthority('GESTIONNAIRERH')")
    public ResponseEntity<List<MembreFamille>> getAllMembres() {
        List<MembreFamille> membres = gestionnaireRhService.getAllMembres();
        return ResponseEntity.ok(membres);
    }
    @GetMapping("/family-situation")
    @PreAuthorize("hasAnyAuthority('GESTIONNAIRERH')")
    public ResponseEntity<Map<SituationFamiliale, Long>> getUsersByFamilySituation() {
        Map<SituationFamiliale, Long> usersBySituation = userService.getUsersByFamilySituation();
        return ResponseEntity.ok(usersBySituation);
    }
    @GetMapping("/family-members-statistics")
    @PreAuthorize("hasAnyAuthority('GESTIONNAIRERH')")

    public ResponseEntity<Map<String, Long>> getFamilyMembersStatistics() {
        Map<String, Long> familyMembersStatistics = userService.getFamilyMembersStatistics();
        return ResponseEntity.ok(familyMembersStatistics);
    }
    @GetMapping("/countByEtat")
    @PreAuthorize("hasAnyAuthority('GESTIONNAIRERH')")
    public ResponseEntity<Map<String, Long>> countDemandesByEtat() {
        Map<String, Long> countsByEtat = gestionnaireRhService.countDemandesByEtat();
        return ResponseEntity.ok(countsByEtat);
    }
    @GetMapping("/countRequestsSituationByEtat")
    @PreAuthorize("hasAnyAuthority('GESTIONNAIRERH')")
    public ResponseEntity<Map<String, Long>> countDemandesSituationByEtat() {
        Map<String, Long> countsByEtat = gestionnaireRhService.countDemandesSituationByEtat();
        return ResponseEntity.ok(countsByEtat);
    }
}


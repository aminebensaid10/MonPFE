package com.mypfeproject.pfe.controller;

import com.mypfeproject.pfe.entities.DemandeDemenagement;
import com.mypfeproject.pfe.entities.DemandeModeTransport;
import com.mypfeproject.pfe.services.GestionnairePaieService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/gestionnairePAIE")
@RequiredArgsConstructor
public class GestionnairePAIEController {
    @Autowired
    private final GestionnairePaieService gestionnairePaieService;
    @GetMapping
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hi Gestionnaire Paie");
    }
    @PostMapping("/valider-demande-mode-transport/{demandeModeTransportId}")
    @PreAuthorize("hasAnyAuthority('GESTIONNAIREPAIE')")
    public ResponseEntity<Void> validerDemandeDemenagement(@PathVariable Long demandeModeTransportId) {
        try {
            gestionnairePaieService.validerDemandeModeTransport(demandeModeTransportId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("/rejeter-demande-mode-transport/{demandeModeTransportId}")
    @PreAuthorize("hasAnyAuthority('GESTIONNAIREPAIE')")
    public ResponseEntity<Void> rejeterDemandeModeTransport(@PathVariable Long demandeModeTransportId) {
        try {
            gestionnairePaieService.rejeterDemandeModeTransport(demandeModeTransportId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/demande-mode-transport/{id}")
    @PreAuthorize("hasAnyAuthority('GESTIONNAIREPAIE')")

    public ResponseEntity<DemandeModeTransport> getDemandeModeTransportById(@PathVariable Long id) {
        Optional<DemandeModeTransport> demandeModeTransport = gestionnairePaieService.getDemandeModeTransportById(id);

        return demandeModeTransport.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/demandes-modes-transports")
    @PreAuthorize("hasAnyAuthority('GESTIONNAIREPAIE')")
    public ResponseEntity<List<DemandeModeTransport>> getAllDemandesModeTransport() {
        try {
            List<DemandeModeTransport> demandeModeTransports = gestionnairePaieService.getAllDemandesModeTransport();
            return ResponseEntity.ok(demandeModeTransports);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

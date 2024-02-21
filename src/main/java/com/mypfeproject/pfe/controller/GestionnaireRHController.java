package com.mypfeproject.pfe.controller;

import com.mypfeproject.pfe.services.GestionnaireRhService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
}


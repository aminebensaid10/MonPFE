package com.mypfeproject.pfe.controller;

import com.mypfeproject.pfe.dto.DemandeCompositionFamilialeDto;
import com.mypfeproject.pfe.entities.User;
import com.mypfeproject.pfe.services.DemandeAjoutFamilleService;
import com.mypfeproject.pfe.services.DemandeCompositionFamilialeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

@RequestMapping("/api/v1/collaborateur")
@RequiredArgsConstructor
public class CollaborateurController {
    private static final Logger logger = LoggerFactory.getLogger(CollaborateurController.class);

    @Autowired
    private final DemandeCompositionFamilialeService demandeCompositionFamilialeService;
    @GetMapping
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hi Collaborateur");
    }

    @PostMapping("/creer-demande-composition-familiale")
    @PreAuthorize("hasAnyAuthority('COLLABORATEUR')")
    public ResponseEntity<Void> creerDemandeCompositionFamiliale(
            @AuthenticationPrincipal User collaborateur,
            @RequestBody DemandeCompositionFamilialeDto demandeDTO
    ) {
        try {
            if (collaborateur != null) {
                logger.info("Demande de composition familiale reçue. Collaborateur : {}", collaborateur.getUsername());
            } else {
                logger.warn("Demande de composition familiale reçue. Collaborateur est null.");
            }

            demandeCompositionFamilialeService.creerDemandeCompositionFamiliale(collaborateur, demandeDTO);

            logger.info("Demande de composition familiale créée avec succès.");

            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            logger.error("Erreur lors de la création de la demande de composition familiale.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



}

package com.mypfeproject.pfe.services.Impl;

import com.mypfeproject.pfe.dto.DemandeCompositionFamilialeDto;
import com.mypfeproject.pfe.entities.Demande;
import com.mypfeproject.pfe.entities.MembreFamille;
import com.mypfeproject.pfe.entities.User;
import com.mypfeproject.pfe.repository.MembreFamilleRepository;
import com.mypfeproject.pfe.services.DemandeAjoutFamilleService;
import com.mypfeproject.pfe.services.DemandeCompositionFamilialeService;
import com.mypfeproject.pfe.services.MembreFamilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class DemandeCompositionFamilialeServiceImpl implements DemandeCompositionFamilialeService {
    @Autowired
    private DemandeAjoutFamilleService demandeAjoutFamilleService;

    @Autowired
    private MembreFamilleService membreFamilleService;

    @Autowired
    AuthenticationServiceImpl authenticationService;

    @Override
    public void creerDemandeCompositionFamiliale(User collaborateur, DemandeCompositionFamilialeDto demandeDTO) {
        MembreFamille membreFamille = new MembreFamille();
        membreFamille.setNomMembre(demandeDTO.getNomMembre());
        membreFamille.setPrenomMembre(demandeDTO.getPrenomMembre());
        membreFamille.setSexe(demandeDTO.getSexe());
        membreFamille.setDateNaissance(demandeDTO.getDateNaissance());
        membreFamille.setLienParente(demandeDTO.getLienParente());
        membreFamille.setJustificatif(demandeDTO.getJustificatif());
        membreFamille.setCommentaire(demandeDTO.getCommentaire());

        membreFamilleService.creerMembreFamille(membreFamille);

        Demande demandeAjoutFamille = new Demande();
        demandeAjoutFamille.setMembreFamille(membreFamille);
        demandeAjoutFamille.setCollaborateur(collaborateur);

            demandeAjoutFamilleService.creerDemandeAjoutFamille(demandeAjoutFamille);

    }





}

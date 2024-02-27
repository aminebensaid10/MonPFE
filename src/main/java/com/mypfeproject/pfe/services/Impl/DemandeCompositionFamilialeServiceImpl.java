package com.mypfeproject.pfe.services.Impl;

import com.mypfeproject.pfe.dto.DemandeCompositionFamilialeDto;
import com.mypfeproject.pfe.entities.Demande;
import com.mypfeproject.pfe.entities.MembreFamille;
import com.mypfeproject.pfe.entities.User;
import com.mypfeproject.pfe.repository.DemandeAjoutFamilleRepository;
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
    @Autowired
    DemandeAjoutFamilleRepository demandeAjoutFamilleRepository ;

    @Override
    public void creerDemandeCompositionFamiliale(User collaborateur, DemandeCompositionFamilialeDto demandeDTO) {
        MembreFamille membreFamille = new MembreFamille();
        membreFamille.setNomMembre(demandeDTO.getNomMembre());
        membreFamille.setPrenomMembre(demandeDTO.getPrenomMembre());
        membreFamille.setSexe(demandeDTO.getSexe());
        membreFamille.setDateNaissance(demandeDTO.getDateNaissance());
        membreFamille.setLienParente(demandeDTO.getLienParente());
        membreFamille.setCommentaire(demandeDTO.getCommentaire());

        membreFamilleService.creerMembreFamille(membreFamille);

        Demande demandeAjoutFamille = new Demande();
        demandeAjoutFamille.setMembreFamille(membreFamille);
        demandeAjoutFamille.setCollaborateur(collaborateur);

            demandeAjoutFamilleService.creerDemandeAjoutFamille(demandeAjoutFamille);

    }
    @Override
    public List<Demande> getDemandesParCollaborateur(User collaborateur) {
        return demandeAjoutFamilleRepository.findByCollaborateur(collaborateur);
    }
    @Override
    public void creerDemandeModification(User collaborateur, Long membreId, DemandeCompositionFamilialeDto demandeDTO) {
        MembreFamille membreFamille = membreFamilleService.getMembreParId(membreId);

        membreFamille.setNomMembre(demandeDTO.getNomMembre());
        membreFamille.setPrenomMembre(demandeDTO.getPrenomMembre());
        membreFamille.setSexe(demandeDTO.getSexe());
        membreFamille.setDateNaissance(demandeDTO.getDateNaissance());
        membreFamille.setLienParente(demandeDTO.getLienParente());
        membreFamille.setCommentaire(demandeDTO.getCommentaire());
        membreFamille.setIsUpdated("En cours de traitement");




        membreFamilleService.mettreAJourMembreFamille(membreId, demandeDTO);

        Demande demandeModification = new Demande();
        demandeModification.setMembreFamille(membreFamille);
        demandeModification.setCollaborateur(collaborateur);
        demandeModification.setEtat("En cours");
        demandeModification.setTypeDemande("Modification en cours");

        demandeAjoutFamilleService.creerDemandeModifcationFamille(demandeModification);
    }





}

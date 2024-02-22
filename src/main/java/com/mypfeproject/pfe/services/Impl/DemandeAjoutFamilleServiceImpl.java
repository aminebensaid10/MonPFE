package com.mypfeproject.pfe.services.Impl;

import com.mypfeproject.pfe.dto.DemandeCompositionFamilialeDto;
import com.mypfeproject.pfe.entities.Demande;
import com.mypfeproject.pfe.entities.MembreFamille;
import com.mypfeproject.pfe.entities.User;
import com.mypfeproject.pfe.repository.DemandeAjoutFamilleRepository;
import com.mypfeproject.pfe.services.DemandeAjoutFamilleService;
import com.mypfeproject.pfe.services.MembreFamilleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DemandeAjoutFamilleServiceImpl implements DemandeAjoutFamilleService {


   @Autowired
    private final DemandeAjoutFamilleRepository demandeAjoutFamilleRepository;


    @Override
    public void creerDemandeAjoutFamille(Demande demande) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User collaborateur = (User) authentication.getPrincipal();
            demande.setCollaborateur(collaborateur);
            MembreFamille membreFamille = demande.getMembreFamille();
            if (membreFamille != null) {
                membreFamille.setCollaborateur(collaborateur);
            }

        }

        demande.setEtat("En cours");
        demande.setTypeDemande("Ajout membre famille");
        demandeAjoutFamilleRepository.save(demande);
    }

}

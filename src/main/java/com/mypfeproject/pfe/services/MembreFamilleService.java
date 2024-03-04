package com.mypfeproject.pfe.services;

import com.mypfeproject.pfe.dto.DemandeCompositionFamilialeDto;
import com.mypfeproject.pfe.entities.MembreFamille;
import com.mypfeproject.pfe.entities.User;

import java.util.List;

public interface MembreFamilleService {
    void creerMembreFamille(MembreFamille membreFamille) ;
    public List<MembreFamille> getMembresParCollaborateur(User collaborateur) ;
    MembreFamille getMembreParId(Long id) ;
    void mettreAJourMembreFamille(Long membreId, DemandeCompositionFamilialeDto demandeDTO) ;
    MembreFamille getMembreById(Long id);
    void supprimerMembreFamille(Long membreId) ;
}

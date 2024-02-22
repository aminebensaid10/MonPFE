package com.mypfeproject.pfe.services.Impl;

import com.mypfeproject.pfe.entities.Demande;
import com.mypfeproject.pfe.entities.MembreFamille;
import com.mypfeproject.pfe.repository.DemandeAjoutFamilleRepository;
import com.mypfeproject.pfe.services.GestionnaireRhService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestionnaireRhServiceImpl implements GestionnaireRhService {
    @Autowired
    private DemandeAjoutFamilleRepository demandeAjoutFamilleRepository;
    @Autowired
    private MembreFamilleServiceImpl membreFamilleService;

    @Override
    @Transactional
    public void validerDemande(Long demandeId) {
        Demande demande = demandeAjoutFamilleRepository.findById(demandeId).orElse(null);

        if (demande != null && "En cours".equals(demande.getEtat())) {
            demande.setEtat("Valide");
            demandeAjoutFamilleRepository.save(demande);

            MembreFamille membreFamille = demande.getMembreFamille();
            if (membreFamille != null) {
                membreFamille.setValide(true);
                membreFamilleService.creerMembreFamille(membreFamille);
            }
        }
    }
}
package com.mypfeproject.pfe.services.Impl;

import com.mypfeproject.pfe.entities.Demande;
import com.mypfeproject.pfe.entities.MembreFamille;
import com.mypfeproject.pfe.repository.DemandeAjoutFamilleRepository;
import com.mypfeproject.pfe.services.GestionnaireRhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestionnaireRhServiceImpl implements GestionnaireRhService {
    @Autowired
    private DemandeAjoutFamilleRepository demandeAjoutFamilleRepository;
    @Autowired
    private MembreFamilleServiceImpl membreFamilleService ;

    @Override
    public void validerDemande(Long demandeId) {
        Demande demande = demandeAjoutFamilleRepository.findById(demandeId).orElse(null);

        if (demande != null && demande.getEtat().equals("En cours")) {

            demande.setEtat("Valide");
            demandeAjoutFamilleRepository.save(demande);

            // Procédez à la création et à l'enregistrement du membre de la famille dans la base de données
            MembreFamille membreFamille = demande.getMembreFamille();
            membreFamilleService.creerMembreFamille(membreFamille);
        }
    }
}

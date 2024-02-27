package com.mypfeproject.pfe.services.Impl;

import com.mypfeproject.pfe.entities.Demande;
import com.mypfeproject.pfe.entities.MembreFamille;
import com.mypfeproject.pfe.repository.DemandeAjoutFamilleRepository;
import com.mypfeproject.pfe.services.GestionnaireRhService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
                membreFamille.setIsUpdated("membre mis a jour");

                membreFamilleService.creerMembreFamille(membreFamille);
            }
        }
    }


    @Override
    @Transactional
    public void rejeterDemande(Long demandeId) {
        Demande demande = demandeAjoutFamilleRepository.findById(demandeId).orElse(null);

        if (demande != null && "En cours".equals(demande.getEtat())) {
            demande.setEtat("Invalide");
            demandeAjoutFamilleRepository.save(demande);

            MembreFamille membreFamille = demande.getMembreFamille();
            if (membreFamille != null) {
                membreFamille.setIsUpdated("membre ne pas mis a jour");

                membreFamilleService.creerMembreFamille(membreFamille);
            }
        }
    }
    @Override
    public Map<Long, List<Demande>> getDemandesGroupedByCollaborateur() {
        List<Demande> toutesDemandes = demandeAjoutFamilleRepository.findAll();

        return toutesDemandes.stream()
                .collect(Collectors.groupingBy(demande -> demande.getCollaborateur().getId()));
    }
    @Override
    public Optional<Demande> getDemandeById(Long demandeId) {
        return demandeAjoutFamilleRepository.findById(demandeId);
    }


}
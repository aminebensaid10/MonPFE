package com.mypfeproject.pfe.services.Impl;

import com.mypfeproject.pfe.dto.DemandeCompositionFamilialeDto;
import com.mypfeproject.pfe.entities.MembreFamille;
import com.mypfeproject.pfe.entities.User;
import com.mypfeproject.pfe.repository.MembreFamilleRepository;
import com.mypfeproject.pfe.services.MembreFamilleService;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MembreFamilleServiceImpl implements MembreFamilleService {
    @Autowired
    MembreFamilleRepository membreFamilleRepository ;

    @Override
    public void creerMembreFamille(MembreFamille membreFamille) {
        membreFamilleRepository.save(membreFamille);
    }
    public List<MembreFamille> getMembresParCollaborateur(User collaborateur) {
        List<String> excludedValues = Arrays.asList("En cours de traitement", "membre ne pas mis a jour");
        return membreFamilleRepository.findByCollaborateurAndValideTrueAndIsUpdatedNotIn(collaborateur, excludedValues);
    }


    @Override
    public MembreFamille getMembreParId(Long id) {
        return membreFamilleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Membre de famille introuvable avec l'ID : " + id));
    }
    @Override
    public void mettreAJourMembreFamille(Long membreId, DemandeCompositionFamilialeDto demandeDTO) {
        MembreFamille membreFamille = getMembreParId(membreId);
        membreFamilleRepository.save(membreFamille);
    }

    @Override
    public MembreFamille getMembreById(Long id) {
        return membreFamilleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Membre de famille introuvable avec l'ID : " + id));
    }
}

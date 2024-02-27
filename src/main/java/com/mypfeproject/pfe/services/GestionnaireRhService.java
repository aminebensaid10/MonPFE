package com.mypfeproject.pfe.services;

import com.mypfeproject.pfe.entities.Demande;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GestionnaireRhService {
    void validerDemande(Long demandeId) ;
    public void rejeterDemande(Long demandeId) ;
    public Map<Long, List<Demande>> getDemandesGroupedByCollaborateur();
    public Optional<Demande> getDemandeById(Long demandeId);
}

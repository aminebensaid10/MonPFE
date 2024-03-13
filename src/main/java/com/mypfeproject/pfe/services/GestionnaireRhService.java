package com.mypfeproject.pfe.services;

import com.mypfeproject.pfe.entities.Demande;
import com.mypfeproject.pfe.entities.DemandeSituationFamiliale;
import com.mypfeproject.pfe.entities.Notification;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GestionnaireRhService {
    void validerDemande(Long demandeId) ;
    public void rejeterDemande(Long demandeId) ;
    public Map<Long, List<Demande>> getDemandesGroupedByCollaborateur();
    public Optional<Demande> getDemandeById(Long demandeId);
    List<DemandeSituationFamiliale> getAllDemandesSituationFamiliale() ;
    List<Notification> getAllNotifications();
    public List<Notification> getAllUnreadNotifications();
    public void validerDemandeSituationFamiliale(Long demandesituationfamiliale_id) ;
    void rejeterDemandeSituation(Long demandesituationfamiliale_id);
    Optional<DemandeSituationFamiliale> getDemandeSituationById(Long demandesituationfamiliale_id) ;
}

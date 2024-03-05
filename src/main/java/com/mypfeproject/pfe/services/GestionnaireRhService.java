package com.mypfeproject.pfe.services;

import com.mypfeproject.pfe.entities.Demande;
import com.mypfeproject.pfe.entities.Notification;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GestionnaireRhService {
    void validerDemande(Long demandeId) ;
    public void rejeterDemande(Long demandeId) ;
    public Map<Long, List<Demande>> getDemandesGroupedByCollaborateur();
    public Optional<Demande> getDemandeById(Long demandeId);
    List<Notification> getAllNotifications();
    public List<Notification> getAllUnreadNotifications();
}

package com.mypfeproject.pfe.services.Impl;

import com.mypfeproject.pfe.entities.Demande;
import com.mypfeproject.pfe.entities.MembreFamille;
import com.mypfeproject.pfe.entities.Notification;
import com.mypfeproject.pfe.repository.DemandeAjoutFamilleRepository;
import com.mypfeproject.pfe.repository.NotificationRepository;
import com.mypfeproject.pfe.services.GestionnaireRhService;
import com.mypfeproject.pfe.services.Impl.CollaborateurServiceImp.MembreFamilleServiceImpl;
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
    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    @Transactional
    public void validerDemande(Long demandeId) {
        Notification notification = notificationRepository.findByDemandeId(demandeId);

        Demande demande = demandeAjoutFamilleRepository.findById(demandeId).orElse(null);

        if (demande != null && "En cours".equals(demande.getEtat())) {
            demande.setEtat("Valide");
            demandeAjoutFamilleRepository.save(demande);
            if (notification != null) {
                notification.setRead(true);
                notificationRepository.save(notification);
            }

            MembreFamille membreFamille = demande.getMembreFamille();
            if (membreFamille != null) {
                membreFamille.setValide(true);

                if ("Modification membre famille".equals(demande.getTypeDemande())) {
                    membreFamille.setIsUpdated("membre mis a jour");

                    membreFamilleService.creerMembreFamille(membreFamille);
                } else if ("Suppression membre famille".equals(demande.getTypeDemande())) {
                    List<Demande> demandesAssociees = demandeAjoutFamilleRepository.findByMembreFamille(membreFamille);

                    for (Demande demandeAssociee : demandesAssociees) {
                        demandeAjoutFamilleRepository.delete(demandeAssociee);
                    }

                    membreFamilleService.supprimerMembreFamille(membreFamille.getId());


                }

            }
        }
    }



    @Override
    @Transactional
    public void rejeterDemande(Long demandeId) {
        Notification notification = notificationRepository.findByDemandeId(demandeId);

        Demande demande = demandeAjoutFamilleRepository.findById(demandeId).orElse(null);

        if (demande != null && "En cours".equals(demande.getEtat())) {
            demande.setEtat("Invalide");
            demandeAjoutFamilleRepository.save(demande);
            if (notification != null) {
                notification.setRead(true);
                notificationRepository.save(notification);
            }

            MembreFamille membreFamille = demande.getMembreFamille();
            if (membreFamille != null) {
                if ("Modification membre famille".equals(demande.getTypeDemande())) {
                    membreFamille.setIsUpdated("membre ne pas mis a jour");

                    membreFamilleService.creerMembreFamille(membreFamille);
                }
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





        @Override
        public List<Notification> getAllNotifications() {
            return notificationRepository.findAll();

    }
    @Override
    public List<Notification> getAllUnreadNotifications() {
        return notificationRepository.findByIsReadFalse();
    }


}
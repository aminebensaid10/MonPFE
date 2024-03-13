package com.mypfeproject.pfe.services.Impl;

import com.mypfeproject.pfe.entities.*;
import com.mypfeproject.pfe.repository.DemandeAjoutFamilleRepository;
import com.mypfeproject.pfe.repository.DemandeSituationFamilialRepository;
import com.mypfeproject.pfe.repository.NotificationRepository;
import com.mypfeproject.pfe.repository.UserRepository;
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
    private DemandeSituationFamilialRepository demandeSituationFamilialRepository;
    @Autowired
    private MembreFamilleServiceImpl membreFamilleService;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private UserRepository userRepository;


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
    public List<DemandeSituationFamiliale> getAllDemandesSituationFamiliale() {
        return demandeSituationFamilialRepository.findAll();
    }

    @Override
    public Optional<Demande> getDemandeById(Long demandeId) {
        return demandeAjoutFamilleRepository.findById(demandeId);
    }
    @Override
    public Optional<DemandeSituationFamiliale> getDemandeSituationById(Long demandesituationfamiliale_id) {
        return demandeSituationFamilialRepository.findById(demandesituationfamiliale_id);
    }





        @Override
        public List<Notification> getAllNotifications() {
            return notificationRepository.findAll();

    }
    @Override
    public List<Notification> getAllUnreadNotifications() {
        return notificationRepository.findByIsReadFalse();
    }

    @Override
    @Transactional
    public void validerDemandeSituationFamiliale(Long demandesituationfamiliale_id) {
        Notification notification = notificationRepository.findByDemandesituationfamilialeId(demandesituationfamiliale_id);

        DemandeSituationFamiliale demandeSituationFamiliale = demandeSituationFamilialRepository.findById(demandesituationfamiliale_id).orElse(null);

        if (demandeSituationFamiliale != null && "En cours".equals(demandeSituationFamiliale.getEtat())) {
            demandeSituationFamiliale.setEtat("Valide");
             demandeSituationFamilialRepository.save(demandeSituationFamiliale);
            if (notification != null) {
                notification.setRead(true);
                notificationRepository.save(notification);
            }

            User collaborateur = demandeSituationFamiliale.getCollaborateur();
            if (collaborateur != null) {
                collaborateur.setSituationFamiliale(demandeSituationFamiliale.getNouvelleSituation());
                collaborateur.setDemandeValidee(true);
                userRepository.save(collaborateur);
            }
        }
    }
    @Override
    @Transactional
    public void rejeterDemandeSituation(Long demandesituationfamiliale_id) {
        Notification notification = notificationRepository.findByDemandesituationfamilialeId(demandesituationfamiliale_id);

        DemandeSituationFamiliale demandeSituationFamiliale = demandeSituationFamilialRepository.findById(demandesituationfamiliale_id).orElse(null);

        if (demandeSituationFamiliale != null && "En cours".equals(demandeSituationFamiliale.getEtat())) {
            demandeSituationFamiliale.setEtat("Invalide");
            demandeSituationFamilialRepository.save(demandeSituationFamiliale);
            if (notification != null) {
                notification.setRead(true);
                notificationRepository.save(notification);
            }



        }
    }


}
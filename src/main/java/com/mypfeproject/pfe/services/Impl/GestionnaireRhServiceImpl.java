package com.mypfeproject.pfe.services.Impl;

import com.mypfeproject.pfe.entities.*;
import com.mypfeproject.pfe.repository.*;
import com.mypfeproject.pfe.services.GestionnaireRhService;
import com.mypfeproject.pfe.services.Impl.CollaborateurServiceImp.MembreFamilleServiceImpl;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
    @Autowired
    private DemandeDemenagementRepository demandeDemenagementRepository ;
    @Autowired
    private MembreFamilleRepository membreFamilleRepository ;


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
    public Optional<DemandeDemenagement> getDemandeDemenagementById(Long demandeDemenagementId) {
        return demandeDemenagementRepository.findById(demandeDemenagementId);
    }






        @Override
        public List<Notification> getAllNotifications() {
            return notificationRepository.findAll();

    }
    @Override
    public List<Notification> getAllUnreadNotifications() {
        return notificationRepository.findByIsReadFalseAndDemandeModeTransportIsNull();
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
    public void validerDemandeDemenagement(Long demandeDemenagementId) {
        Notification notification = notificationRepository.findByDemandeDemenagementId(demandeDemenagementId);

        DemandeDemenagement demandeDemenagement = demandeDemenagementRepository.findById(demandeDemenagementId).orElse(null);

        if (demandeDemenagement != null && "En cours".equals(demandeDemenagement.getEtat())) {
            demandeDemenagement.setEtat("Valide");
            demandeDemenagementRepository.save(demandeDemenagement);

            if (notification != null) {
                notification.setRead(true);
                notificationRepository.save(notification);
            }

            User collaborateur = demandeDemenagement.getCollaborateur();
            if (collaborateur != null) {
                collaborateur.setAdressePrincipale(demandeDemenagement.getNouvelleAdresse());
                collaborateur.setDemandeValideeDemenagment(true);
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
    @Override
    @Transactional
    public void rejeterDemandeDemenagement(Long demandeDemenagementId) {
        Notification notification = notificationRepository.findByDemandeDemenagementId(demandeDemenagementId);

        DemandeDemenagement demandeDemenagement = demandeDemenagementRepository.findById(demandeDemenagementId).orElse(null);

        if (demandeDemenagement != null && "En cours".equals(demandeDemenagement.getEtat())) {
            demandeDemenagement.setEtat("Invalide");
            demandeDemenagementRepository.save(demandeDemenagement);
            if (notification != null) {
                notification.setRead(true);
                notificationRepository.save(notification);
            }
        }
    }
    @Override
    public List<User> getAllCollaborateursWithSituationFamiliale() {
        return userRepository.findAllByRole(Role.COLLABORATEUR);
    }
    @Override
    public List<DemandeDemenagement> getAllDemandesDemenagement() {
        return demandeDemenagementRepository.findAll();
    }
    @Override
    public List<MembreFamille> getAllMembres() {
        return membreFamilleRepository.findAll();
    }
    public Map<String, Long> countDemandesByEtat() {
        List<Demande> demandes = demandeAjoutFamilleRepository.findAll();

        Map<String, Long> countsByEtat = new HashMap<>();

        for (Demande demande : demandes) {
            String etat = demande.getEtat();
            if (etat != null) {
                countsByEtat.put(etat, countsByEtat.getOrDefault(etat, 0L) + 1);
            }
        }

        return countsByEtat;
    }
    public Map<String, Long> countDemandesSituationByEtat() {
        List<DemandeSituationFamiliale> demandes = demandeSituationFamilialRepository.findAll();

        Map<String, Long> countsByEtat = new HashMap<>();

        for (DemandeSituationFamiliale demandeSituationFamiliale : demandes) {
            String etat = demandeSituationFamiliale.getEtat();
            if (etat != null) {
                countsByEtat.put(etat, countsByEtat.getOrDefault(etat, 0L) + 1);
            }
        }

        return countsByEtat;
    }
    public Map<String,Long> countRequestsDemenagement(){
        List<DemandeDemenagement> demandeDemenagements= demandeDemenagementRepository.findAll();
        Map<String,Long> countsbyetat= new HashMap<>() ;
        for (DemandeDemenagement demandeDemenagement : demandeDemenagements) {
            String etat = demandeDemenagement.getEtat();
            if (etat != null) {
                countsbyetat.put(etat, countsbyetat.getOrDefault(etat, 0L) + 1);
            }
        }

        return countsbyetat;
    }
}
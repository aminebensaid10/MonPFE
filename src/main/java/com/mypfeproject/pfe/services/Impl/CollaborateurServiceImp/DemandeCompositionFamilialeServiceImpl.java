package com.mypfeproject.pfe.services.Impl.CollaborateurServiceImp;

import com.mypfeproject.pfe.dto.DemandeCompositionFamilialeDto;
import com.mypfeproject.pfe.entities.Demande;
import com.mypfeproject.pfe.entities.MembreFamille;
import com.mypfeproject.pfe.entities.Notification;
import com.mypfeproject.pfe.entities.User;
import com.mypfeproject.pfe.repository.DemandeAjoutFamilleRepository;
import com.mypfeproject.pfe.services.DemandeAjoutFamilleService;
import com.mypfeproject.pfe.services.DemandeCompositionFamilialeService;
import com.mypfeproject.pfe.services.Impl.AuthenticationServiceImpl;
import com.mypfeproject.pfe.services.MembreFamilleService;
import com.mypfeproject.pfe.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemandeCompositionFamilialeServiceImpl implements DemandeCompositionFamilialeService {
    @Autowired
    private DemandeAjoutFamilleService demandeAjoutFamilleService;

    @Autowired
    private MembreFamilleService membreFamilleService;

    @Autowired
    AuthenticationServiceImpl authenticationService;
    @Autowired
    DemandeAjoutFamilleRepository demandeAjoutFamilleRepository ;
    @Autowired
    private NotificationService notificationService;

    @Override
    public void creerDemandeCompositionFamiliale(User collaborateur, DemandeCompositionFamilialeDto demandeDTO) {
        MembreFamille membreFamille = new MembreFamille();
        membreFamille.setNomMembre(demandeDTO.getNomMembre());
        membreFamille.setPrenomMembre(demandeDTO.getPrenomMembre());
        membreFamille.setSexe(demandeDTO.getSexe());
        membreFamille.setDateNaissance(demandeDTO.getDateNaissance());
        membreFamille.setLienParente(demandeDTO.getLienParente());
        membreFamille.setCommentaire(demandeDTO.getCommentaire());

        membreFamilleService.creerMembreFamille(membreFamille);

        Demande demandeAjoutFamille = new Demande();
        demandeAjoutFamille.setMembreFamille(membreFamille);
        demandeAjoutFamille.setCollaborateur(collaborateur);
        demandeAjoutFamilleService.creerDemandeAjoutFamille(demandeAjoutFamille);

        Notification notification = new Notification();
        notification.setCollaborateur(collaborateur);
        notification.setMessage("Nouvelle demande d'ajout de membre de famille créée");
        notification.setRead(false);
        notification.setDemande(demandeAjoutFamille);
        notificationService.creerNotification(notification);

    }
    @Override
    public List<Demande> getDemandesParCollaborateur(User collaborateur) {
        return demandeAjoutFamilleRepository.findByCollaborateur(collaborateur);
    }
    @Override
    public void creerDemandeModification(User collaborateur, Long membreId, DemandeCompositionFamilialeDto demandeDTO) {
        MembreFamille membreFamille = membreFamilleService.getMembreParId(membreId);

        membreFamille.setNomMembre(demandeDTO.getNomMembre());
        membreFamille.setPrenomMembre(demandeDTO.getPrenomMembre());
        membreFamille.setSexe(demandeDTO.getSexe());
        membreFamille.setDateNaissance(demandeDTO.getDateNaissance());
        membreFamille.setLienParente(demandeDTO.getLienParente());
        membreFamille.setCommentaire(demandeDTO.getCommentaire());
        membreFamille.setIsUpdated("En cours de traitement");




        membreFamilleService.mettreAJourMembreFamille(membreId, demandeDTO);

        Demande demandeModification = new Demande();
        demandeModification.setMembreFamille(membreFamille);
        demandeModification.setCollaborateur(collaborateur);
        demandeModification.setEtat("En cours");
        demandeModification.setTypeDemande("Modification en cours");

        demandeAjoutFamilleService.creerDemandeModifcationFamille(demandeModification);


        Notification notification = new Notification();
        notification.setCollaborateur(collaborateur);
        notification.setMessage("Nouvelle demande de modfication de membre de famille créée");
        notification.setRead(false);
        notification.setDemande(demandeModification);
        notificationService.creerNotification(notification);
    }


    @Override
    public void creerDemandeSuppression(User collaborateur, Long membreId) {
        MembreFamille membreFamille = membreFamilleService.getMembreParId(membreId);

        if (membreFamille != null) {
            Demande demandeSuppression = new Demande();
            demandeSuppression.setMembreFamille(membreFamille);
            demandeSuppression.setCollaborateur(collaborateur);
            demandeSuppression.setEtat("En cours");
            demandeSuppression.setTypeDemande("Suppression membre famille");

            demandeAjoutFamilleService.creerDemandeSuppressionFamille(demandeSuppression);

            Notification notification = new Notification();
            notification.setCollaborateur(collaborateur);
            notification.setMessage("Nouvelle demande de suppression de membre de famille créée");
            notification.setRead(false);
            notification.setDemande(demandeSuppression);
            notificationService.creerNotification(notification);

        }
    }


}

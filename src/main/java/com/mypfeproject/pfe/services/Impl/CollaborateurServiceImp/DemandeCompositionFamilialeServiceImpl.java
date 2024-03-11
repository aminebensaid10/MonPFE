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
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

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
    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public void creerDemandeCompositionFamiliale(User collaborateur, DemandeCompositionFamilialeDto demandeDTO) {
        MembreFamille membreFamille = new MembreFamille();
        membreFamille.setNomMembre(demandeDTO.getNomMembre());
        membreFamille.setPrenomMembre(demandeDTO.getPrenomMembre());
       membreFamille.setSexe(demandeDTO.getSexe());
        membreFamille.setDateNaissance(demandeDTO.getDateNaissance());
        membreFamille.setLienParente(demandeDTO.getLienParente());
        membreFamille.setCommentaire(demandeDTO.getCommentaire());
        if (demandeDTO.getImageMembre() != null) {
            String imageFileName = UUID.randomUUID().toString() + "_" + demandeDTO.getImageMembre().getOriginalFilename();
            Path imagePath = Paths.get(uploadPath).resolve(imageFileName);

            try (InputStream imageInputStream = demandeDTO.getImageMembre().getInputStream()) {
                Files.copy(imageInputStream, imagePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException("Erreur lors de la copie de l'image du membre vers la destination", e);
            }

            membreFamille.setImagePath(imageFileName);
        }

        if (demandeDTO.getJustificatifFile() != null) {
            String fileName = UUID.randomUUID().toString() + "_" + demandeDTO.getJustificatifFile().getOriginalFilename();
            Path filePath = Paths.get(uploadPath).resolve(fileName);


            try (InputStream inputStream = demandeDTO.getJustificatifFile().getInputStream()) {
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException("Erreur lors de la copie du fichier justificatif vers la destination", e);
            }

            membreFamille.setJustificatifPath(fileName);
        }

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
        if (demandeDTO.getImageMembre() != null) {
            String imageName = UUID.randomUUID() + "_" + demandeDTO.getImageMembre().getOriginalFilename();
            String imagePath = "fichiers/" + imageName;

            try {
                File targetFile = new File(imagePath);
                FileUtils.writeByteArrayToFile(targetFile, demandeDTO.getImageMembre().getBytes());

                membreFamille.setImagePath("/images/" + imageName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }




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

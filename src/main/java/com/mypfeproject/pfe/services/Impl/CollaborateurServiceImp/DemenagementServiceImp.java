package com.mypfeproject.pfe.services.Impl.CollaborateurServiceImp;

import com.mypfeproject.pfe.dto.DemandeDemenagementDto;
import com.mypfeproject.pfe.dto.DemandeSituationFamilialeDTO;
import com.mypfeproject.pfe.entities.DemandeDemenagement;
import com.mypfeproject.pfe.entities.DemandeSituationFamiliale;
import com.mypfeproject.pfe.entities.Notification;
import com.mypfeproject.pfe.entities.User;
import com.mypfeproject.pfe.repository.DemandeDemenagementRepository;
import com.mypfeproject.pfe.repository.UserRepository;
import com.mypfeproject.pfe.services.DemenagementService;
import com.mypfeproject.pfe.services.GestionnaireRhService;
import com.mypfeproject.pfe.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@Service
public class DemenagementServiceImp implements DemenagementService {
    @Value("${upload.path}")
    private String uploadPath;
    @Autowired
    DemandeDemenagementRepository demandeDemenagementRepository ;
    @Autowired
    UserRepository userRepository;
    @Autowired
    NotificationService notificationService ;
    @Override
    public void creerDemandeDemenagement(User collaborateur, DemandeDemenagementDto demandeDTO) {
        if (collaborateur.getAdressePrincipale() != null) {

            throw new RuntimeException("L'adresse prinicpale est déjà définie pour le collaborateur.");
        }

        DemandeDemenagement demandeDemenagement = new DemandeDemenagement();
        demandeDemenagement.setCollaborateur(collaborateur);
        demandeDemenagement.setNouvelleAdresse(demandeDTO.getNouvelleAdresse());
        demandeDemenagement.setEtat("En cours");
        demandeDemenagement.setTypeDemande("Ajout adresse prinicipal");

        if (demandeDTO.getJustificatifAdressePrincipale() != null) {
            String justificatifFileName = UUID.randomUUID().toString() + "_" + demandeDTO.getJustificatifAdressePrincipale().getOriginalFilename();
            Path justificatifPath = Paths.get(uploadPath).resolve(justificatifFileName);

            try (InputStream justificatifInputStream = demandeDTO.getJustificatifAdressePrincipale().getInputStream()) {
                Files.copy(justificatifInputStream, justificatifPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException("Erreur lors de la copie du justificatif vers la destination", e);
            }

            demandeDemenagement.setJustificatifPath(justificatifFileName);
        }

        demandeDemenagementRepository.save(demandeDemenagement);

        collaborateur.setAdressePrincipale(demandeDTO.getNouvelleAdresse());
        collaborateur.setDemandeValideeDemenagment(false);

        userRepository.save(collaborateur);
        Notification notification = new Notification();
        notification.setCollaborateur(collaborateur);
        notification.setMessage("Nouvelle demande d'ajout d'adresse principale créée");
        notification.setRead(false);
        notification.setDemandeDemenagement(demandeDemenagement);
        notificationService.creerNotification(notification);
    }
    @Override
    public void creerDemandeSuppressionDemenagement(User collaborateur) {
        DemandeDemenagement demandeDemenagement = new DemandeDemenagement();
        demandeDemenagement.setCollaborateur(collaborateur);
        demandeDemenagement.setEtat("En cours");
        demandeDemenagement.setTypeDemande("Suppression déménagement");

        demandeDemenagementRepository.save(demandeDemenagement);

        if ("Valide".equals(demandeDemenagement.getEtat())) {
            collaborateur.setAdressePrincipale(null); // Supprime l'adresse principale
            collaborateur.setDemandeValidee(false);
            userRepository.save(collaborateur);
        }

        Notification notification = new Notification();
        notification.setCollaborateur(collaborateur);
        notification.setMessage("Nouvelle demande de suppression de déménagement créée");
        notification.setRead(false);
        notification.setDemandeDemenagement(demandeDemenagement);
        notificationService.creerNotification(notification);
    }
    @Override
    public String getAdressPrincipal(String userEmail) {
        Optional<User> optionalUser = userRepository.findByEmail(userEmail);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (user.isDemandeValideeDemenagment()) {
                return user.getAdressePrincipale();
            } else {
                return "Non disponible pour le moment";
            }
        }

        return null;
    }

}

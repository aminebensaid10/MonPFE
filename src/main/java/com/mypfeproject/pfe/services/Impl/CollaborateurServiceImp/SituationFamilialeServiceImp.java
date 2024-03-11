package com.mypfeproject.pfe.services.Impl.CollaborateurServiceImp;

import com.mypfeproject.pfe.dto.DemandeSituationFamilialeDTO;
import com.mypfeproject.pfe.entities.DemandeSituationFamiliale;
import com.mypfeproject.pfe.entities.Notification;
import com.mypfeproject.pfe.entities.User;
import com.mypfeproject.pfe.repository.DemandeSituationFamilialRepository;
import com.mypfeproject.pfe.repository.UserRepository;
import com.mypfeproject.pfe.services.NotificationService;
import com.mypfeproject.pfe.services.SituationFamilialeService;
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

public class SituationFamilialeServiceImp implements SituationFamilialeService {
    @Autowired
    private DemandeSituationFamilialRepository demandeSituationFamilialeRepository;

    @Autowired
    private UserRepository userRepository;
    @Value("${upload.path}")
    private String uploadPath;
    @Autowired
    private NotificationService notificationService;
    @Override
    public void creerDemandeSituationFamiliale(User collaborateur, DemandeSituationFamilialeDTO demandeDTO) {
        // Vérifier si la situation familiale dans la table user est nulle
        if (collaborateur.getSituationFamiliale() != null) {
            // Retourner ou lever une exception indiquant que la situation familiale est déjà définie
            // Par exemple :
            throw new RuntimeException("La situation familiale est déjà définie pour le collaborateur.");
        }

        // Continuer le traitement si la situation familiale est nulle
        DemandeSituationFamiliale demandeSituationFamiliale = new DemandeSituationFamiliale();
        demandeSituationFamiliale.setCollaborateur(collaborateur);
        demandeSituationFamiliale.setNouvelleSituation(demandeDTO.getNouvelleSituation());
        demandeSituationFamiliale.setEtat("En cours");
        demandeSituationFamiliale.setTypeDemande("Ajout situation familiale");

        if (demandeDTO.getJustificatifSituationFamiliale() != null) {
            String justificatifFileName = UUID.randomUUID().toString() + "_" + demandeDTO.getJustificatifSituationFamiliale().getOriginalFilename();
            Path justificatifPath = Paths.get(uploadPath).resolve(justificatifFileName);

            try (InputStream justificatifInputStream = demandeDTO.getJustificatifSituationFamiliale().getInputStream()) {
                Files.copy(justificatifInputStream, justificatifPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException("Erreur lors de la copie du justificatif vers la destination", e);
            }

            demandeSituationFamiliale.setJustificatifPath(justificatifFileName);
        }

        demandeSituationFamilialeRepository.save(demandeSituationFamiliale);

        collaborateur.setSituationFamiliale(demandeDTO.getNouvelleSituation());
        userRepository.save(collaborateur);
        Notification notification = new Notification();
        notification.setCollaborateur(collaborateur);
        notification.setMessage("Nouvelle demande d'ajout de situation familiale créée");
        notification.setRead(false);
        notification.setDemandesituationfamiliale(demandeSituationFamiliale);
        notificationService.creerNotification(notification);
    }

    @Override
    public String getSituationFamiliale(String userEmail) {
        Optional<User> optionalUser = userRepository.findByEmail(userEmail);
        return optionalUser.map(user -> user.getSituationFamiliale().name()).orElse(null);
    }
}

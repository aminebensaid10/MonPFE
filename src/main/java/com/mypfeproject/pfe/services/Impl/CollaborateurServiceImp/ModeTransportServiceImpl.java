package com.mypfeproject.pfe.services.Impl.CollaborateurServiceImp;

import com.mypfeproject.pfe.dto.DemandeDemenagementDto;
import com.mypfeproject.pfe.dto.DemandeModeTransportDto;
import com.mypfeproject.pfe.entities.DemandeDemenagement;
import com.mypfeproject.pfe.entities.DemandeModeTransport;
import com.mypfeproject.pfe.entities.Notification;
import com.mypfeproject.pfe.entities.User;
import com.mypfeproject.pfe.repository.DemandeModeTransportRepository;
import com.mypfeproject.pfe.repository.UserRepository;
import com.mypfeproject.pfe.services.ModeTransportService;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ModeTransportServiceImpl implements ModeTransportService {
    @Autowired
    private DemandeModeTransportRepository modeTransportRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    NotificationService notificationService ;
    @Value("${upload.path}")
    private String uploadPath;
    @Override
    public void creerDemandeModeTransport(User collaborateur, DemandeModeTransportDto demandeDTO) {
        if (collaborateur.getModeDeTransport() != null) {

            throw new RuntimeException("La mode du transport est déjà définie pour le collaborateur.");
        }

        DemandeModeTransport demandeModeTransport = new DemandeModeTransport();
        demandeModeTransport.setCollaborateur(collaborateur);
        demandeModeTransport.setTypeTransport(demandeDTO.getTypeTransport());
        demandeModeTransport.setEtat("En cours");
        demandeModeTransport.setTypeDemande("Ajout mode transport");

        if (demandeDTO.getJustificatifModeTransport() != null) {
            String justificatifFileName = UUID.randomUUID().toString() + "_" + demandeDTO.getJustificatifModeTransport().getOriginalFilename();
            Path justificatifPath = Paths.get(uploadPath).resolve(justificatifFileName);

            try (InputStream justificatifInputStream = demandeDTO.getJustificatifModeTransport().getInputStream()) {
                Files.copy(justificatifInputStream, justificatifPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException("Erreur lors de la copie du justificatif vers la destination", e);
            }

            demandeModeTransport.setJustificatifPath(justificatifFileName);
        }

        modeTransportRepository.save(demandeModeTransport);

        collaborateur.setModeDeTransport(demandeDTO.getTypeTransport());
        collaborateur.setDemandeValideeModeTransport(false);

        userRepository.save(collaborateur);
        Notification notification = new Notification();
        notification.setCollaborateur(collaborateur);
        notification.setMessage("Nouvelle demande d'ajout de mode du transport créée");
        notification.setRead(false);
        notification.setDemandeModeTransport(demandeModeTransport);
        notificationService.creerNotification(notification);
    }
    @Override
    public void creerDemandeSuppressionModeTransport(User collaborateur) {
        DemandeModeTransport demandeModeTransport = new DemandeModeTransport();
        demandeModeTransport.setCollaborateur(collaborateur);
        demandeModeTransport.setEtat("En cours");
        demandeModeTransport.setTypeDemande("Suppression mode du transport");

        modeTransportRepository.save(demandeModeTransport);

        if ("Valide".equals(demandeModeTransport.getEtat())) {
            collaborateur.setAdressePrincipale(null);
            collaborateur.setDemandeValideeModeTransport(false);
            userRepository.save(collaborateur);
        }

        Notification notification = new Notification();
        notification.setCollaborateur(collaborateur);
        notification.setMessage("Nouvelle demande de suppression du mode de transport créée");
        notification.setRead(false);
        notification.setDemandeModeTransport(demandeModeTransport);
        notificationService.creerNotification(notification);
    }
    @Override
    public String getModeTransport(String userEmail) {
        Optional<User> optionalUser = userRepository.findByEmail(userEmail);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (user.isDemandeValideeModeTransport()) {
                return user.getModeDeTransport();
            } else {
                return "Non disponible pour le moment";
            }
        }

        return null;
    }
    @Override
    public List<DemandeModeTransport> getDemandesModeTransportParCollaborateur(User collaborateur) {
        return modeTransportRepository.findByCollaborateur(collaborateur);
    }
    @Override
    public void creerDemandeModificationModeTransport(User collaborateur, DemandeModeTransportDto demandeDTO) {
        if (collaborateur.getModeDeTransport()== null ) {

            throw new RuntimeException("Vous n'avez pas encore un mode de transport.");
        }

        DemandeModeTransport demandeModeTransport = new DemandeModeTransport();
        demandeModeTransport.setCollaborateur(collaborateur);
        demandeModeTransport.setTypeTransport(demandeDTO.getTypeTransport());
        demandeModeTransport.setEtat("En cours");
        demandeModeTransport.setTypeDemande("Modification Mode Transport");

        if (demandeDTO.getJustificatifModeTransport() != null) {
            String justificatifFileName = UUID.randomUUID().toString() + "_" + demandeDTO.getJustificatifModeTransport().getOriginalFilename();
            Path justificatifPath = Paths.get(uploadPath).resolve(justificatifFileName);

            try (InputStream justificatifInputStream = demandeDTO.getJustificatifModeTransport().getInputStream()) {
                Files.copy(justificatifInputStream, justificatifPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException("Erreur lors de la copie du justificatif vers la destination", e);
            }

            demandeModeTransport.setJustificatifPath(justificatifFileName);
        }

        modeTransportRepository.save(demandeModeTransport);
        if ("Valide".equals(demandeModeTransport.getEtat())) {
            collaborateur.setModeDeTransport(demandeDTO.getTypeTransport());
            collaborateur.setDemandeValideeModeTransport(false);

            userRepository.save(collaborateur);
        }

        Notification notification = new Notification();
        notification.setCollaborateur(collaborateur);
        notification.setMessage("Nouvelle demande de modification du mode de transport créee");
        notification.setRead(false);
        notification.setDemandeModeTransport(demandeModeTransport);
        notificationService.creerNotification(notification);
    }
}

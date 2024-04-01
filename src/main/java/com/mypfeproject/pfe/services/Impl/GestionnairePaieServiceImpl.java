package com.mypfeproject.pfe.services.Impl;

import com.mypfeproject.pfe.entities.DemandeDemenagement;
import com.mypfeproject.pfe.entities.DemandeModeTransport;
import com.mypfeproject.pfe.entities.Notification;
import com.mypfeproject.pfe.entities.User;
import com.mypfeproject.pfe.repository.*;
import com.mypfeproject.pfe.services.GestionnairePaieService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GestionnairePaieServiceImpl implements GestionnairePaieService {



    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DemandeModeTransportRepository demandeModeTransportRepository ;
    @Override
    @Transactional
    public void validerDemandeModeTransport(Long demandeModeTransportId) {
        Notification notification = notificationRepository.findByDemandeModeTransportId(demandeModeTransportId);

        DemandeModeTransport demandeModeTransport = demandeModeTransportRepository.findById(demandeModeTransportId).orElse(null);

        if (demandeModeTransport != null && "En cours".equals(demandeModeTransport.getEtat())) {
            demandeModeTransport.setEtat("Valide");
            demandeModeTransportRepository.save(demandeModeTransport);

            if (notification != null) {
                notification.setRead(true);
                notificationRepository.save(notification);
            }

            User collaborateur = demandeModeTransport.getCollaborateur();
            if (collaborateur != null) {
                collaborateur.setModeDeTransport(demandeModeTransport.getTypeTransport());
                collaborateur.setDemandeValideeModeTransport(true);
                userRepository.save(collaborateur);
            }
        }
    }
    @Override
    @Transactional
    public void rejeterDemandeModeTransport(Long demandeModeTransportId) {
        Notification notification = notificationRepository.findByDemandeModeTransportId(demandeModeTransportId);

        DemandeModeTransport demandeModeTransport = demandeModeTransportRepository.findById(demandeModeTransportId).orElse(null);

        if (demandeModeTransport != null && "En cours".equals(demandeModeTransport.getEtat())) {
            demandeModeTransport.setEtat("Invalide");
            demandeModeTransportRepository.save(demandeModeTransport);
            if (notification != null) {
                notification.setRead(true);
                notificationRepository.save(notification);
            }
        }
    }
    @Override
    public Optional<DemandeModeTransport> getDemandeModeTransportById(Long demandeModeTransportId) {
        return demandeModeTransportRepository.findById(demandeModeTransportId);
    }
    public List<DemandeModeTransport> getAllDemandesModeTransport() {
        return demandeModeTransportRepository.findAll();
    }

}

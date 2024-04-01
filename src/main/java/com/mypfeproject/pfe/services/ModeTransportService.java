package com.mypfeproject.pfe.services;

import com.mypfeproject.pfe.dto.DemandeModeTransportDto;
import com.mypfeproject.pfe.entities.DemandeModeTransport;
import com.mypfeproject.pfe.entities.User;

import java.util.List;

public interface ModeTransportService {
    void creerDemandeModeTransport(User collaborateur, DemandeModeTransportDto demandeDTO);
    void creerDemandeSuppressionModeTransport(User collaborateur) ;
    String getModeTransport(String userEmail) ;
    List<DemandeModeTransport> getDemandesModeTransportParCollaborateur(User collaborateur);
    void creerDemandeModificationModeTransport(User collaborateur, DemandeModeTransportDto demandeDTO);
}

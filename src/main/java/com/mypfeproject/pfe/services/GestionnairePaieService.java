package com.mypfeproject.pfe.services;

import com.mypfeproject.pfe.entities.DemandeModeTransport;

import java.util.List;
import java.util.Optional;

public interface GestionnairePaieService {
    void validerDemandeModeTransport(Long demandeModeTransportId) ;
    void rejeterDemandeModeTransport(Long demandeModeTransportId);
    Optional<DemandeModeTransport> getDemandeModeTransportById(Long demandeModeTransportId);
    List<DemandeModeTransport> getAllDemandesModeTransport();
}

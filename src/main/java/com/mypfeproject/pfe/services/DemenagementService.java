package com.mypfeproject.pfe.services;

import com.mypfeproject.pfe.dto.DemandeDemenagementDto;
import com.mypfeproject.pfe.entities.User;

public interface DemenagementService {
    void creerDemandeDemenagement(User collaborateur, DemandeDemenagementDto demandeDTO);
    public void creerDemandeSuppressionDemenagement(User collaborateur) ;
    String getAdressPrincipal(String userEmail) ;
}

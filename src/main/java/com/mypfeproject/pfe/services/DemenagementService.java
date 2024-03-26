package com.mypfeproject.pfe.services;

import com.mypfeproject.pfe.dto.DemandeDemenagementDto;
import com.mypfeproject.pfe.entities.DemandeDemenagement;
import com.mypfeproject.pfe.entities.User;

import java.util.List;

public interface DemenagementService {
    void creerDemandeDemenagement(User collaborateur, DemandeDemenagementDto demandeDTO);
    public void creerDemandeSuppressionDemenagement(User collaborateur) ;
    String getAdressPrincipal(String userEmail) ;
    List<DemandeDemenagement> getDemandesDemenagementParCollaborateur(User collaborateur) ;
    void declarerDemenagement(User collaborateur, DemandeDemenagementDto demandeDTO) ;
}

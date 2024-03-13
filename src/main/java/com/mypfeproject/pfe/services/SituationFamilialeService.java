package com.mypfeproject.pfe.services;

import com.mypfeproject.pfe.dto.DemandeSituationFamilialeDTO;
import com.mypfeproject.pfe.entities.Demande;
import com.mypfeproject.pfe.entities.DemandeSituationFamiliale;
import com.mypfeproject.pfe.entities.User;

import java.util.List;

public interface SituationFamilialeService {
    void creerDemandeSituationFamiliale(User collaborateur, DemandeSituationFamilialeDTO demandeDTO) ;
    public String getSituationFamiliale(String userEmail) ;
    List<DemandeSituationFamiliale> getDemandesSituationParCollaborateur(User collaborateur);
    void creerDemandeModificationSituationFamiliale(User collaborateur, DemandeSituationFamilialeDTO demandeDTO) ;
    public void creerDemandeSuppressionSituationFamiliale(User collaborateur) ;
}

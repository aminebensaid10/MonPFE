package com.mypfeproject.pfe.services;

import com.mypfeproject.pfe.dto.DemandeSituationFamilialeDTO;
import com.mypfeproject.pfe.entities.User;

public interface SituationFamilialeService {
    void creerDemandeSituationFamiliale(User collaborateur, DemandeSituationFamilialeDTO demandeDTO) ;
    public String getSituationFamiliale(String userEmail) ;
}

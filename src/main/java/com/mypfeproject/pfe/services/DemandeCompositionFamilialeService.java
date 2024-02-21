package com.mypfeproject.pfe.services;

import com.mypfeproject.pfe.dto.DemandeCompositionFamilialeDto;
import com.mypfeproject.pfe.entities.Demande;
import com.mypfeproject.pfe.entities.User;

public interface DemandeCompositionFamilialeService {
    void creerDemandeCompositionFamiliale(User collaborateur, DemandeCompositionFamilialeDto demandeDTO) ;
}

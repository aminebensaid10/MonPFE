package com.mypfeproject.pfe.repository;

import com.mypfeproject.pfe.entities.Demande;
import com.mypfeproject.pfe.entities.DemandeSituationFamiliale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemandeSituationFamilialRepository extends JpaRepository<DemandeSituationFamiliale,Long> {
}

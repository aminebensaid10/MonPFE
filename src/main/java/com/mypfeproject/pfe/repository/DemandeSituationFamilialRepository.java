package com.mypfeproject.pfe.repository;

import com.mypfeproject.pfe.entities.Demande;
import com.mypfeproject.pfe.entities.DemandeSituationFamiliale;
import com.mypfeproject.pfe.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DemandeSituationFamilialRepository extends JpaRepository<DemandeSituationFamiliale,Long> {
    List<DemandeSituationFamiliale> findByCollaborateur(User collaborateur);

}

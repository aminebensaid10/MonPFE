package com.mypfeproject.pfe.repository;

import com.mypfeproject.pfe.entities.Demande;
import com.mypfeproject.pfe.entities.DemandeDemenagement;
import com.mypfeproject.pfe.entities.DemandeSituationFamiliale;
import com.mypfeproject.pfe.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DemandeDemenagementRepository extends JpaRepository<DemandeDemenagement,Long> {
    List<DemandeDemenagement> findByCollaborateur(User collaborateur);

}

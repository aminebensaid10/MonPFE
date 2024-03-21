package com.mypfeproject.pfe.repository;

import com.mypfeproject.pfe.entities.Demande;
import com.mypfeproject.pfe.entities.DemandeDemenagement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemandeDemenagementRepository extends JpaRepository<DemandeDemenagement,Long> {
}

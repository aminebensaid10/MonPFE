package com.mypfeproject.pfe.repository;

import com.mypfeproject.pfe.entities.MembreFamille;
import com.mypfeproject.pfe.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MembreFamilleRepository extends JpaRepository<MembreFamille,Long> {
    List<MembreFamille> findByCollaborateurAndValideTrue(User collaborateur);

}

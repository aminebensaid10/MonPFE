package com.mypfeproject.pfe.repository;

import com.mypfeproject.pfe.entities.Demande;
import com.mypfeproject.pfe.entities.MembreFamille;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemandeAjoutFamilleRepository extends JpaRepository<Demande,Long> {
}

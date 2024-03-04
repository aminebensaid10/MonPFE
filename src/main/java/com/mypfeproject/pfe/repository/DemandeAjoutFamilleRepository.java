package com.mypfeproject.pfe.repository;

import com.mypfeproject.pfe.entities.Demande;
import com.mypfeproject.pfe.entities.MembreFamille;
import com.mypfeproject.pfe.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface DemandeAjoutFamilleRepository extends JpaRepository<Demande,Long> {
    @Query("SELECT d.collaborateur.id, d FROM Demande d GROUP BY d.collaborateur.id")
    Map<Long, List<Demande>> getDemandesGroupedByCollaborateur();
    List<Demande> findByCollaborateur(User collaborateur);
    List<Demande> findByMembreFamille(MembreFamille membreFamille);


}

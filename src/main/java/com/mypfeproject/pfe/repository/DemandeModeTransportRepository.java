package com.mypfeproject.pfe.repository;

import com.mypfeproject.pfe.entities.DemandeDemenagement;
import com.mypfeproject.pfe.entities.DemandeModeTransport;
import com.mypfeproject.pfe.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DemandeModeTransportRepository extends JpaRepository<DemandeModeTransport,Long> {
    List<DemandeModeTransport> findByCollaborateur(User collaborateur);

}

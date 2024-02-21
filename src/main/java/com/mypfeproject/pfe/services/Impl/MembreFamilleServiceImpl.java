package com.mypfeproject.pfe.services.Impl;

import com.mypfeproject.pfe.entities.MembreFamille;
import com.mypfeproject.pfe.repository.MembreFamilleRepository;
import com.mypfeproject.pfe.services.MembreFamilleService;
import jakarta.persistence.Entity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MembreFamilleServiceImpl implements MembreFamilleService {
    @Autowired
    MembreFamilleRepository membreFamilleRepository ;

    @Override
    public void creerMembreFamille(MembreFamille membreFamille) {
        membreFamilleRepository.save(membreFamille);
    }


}

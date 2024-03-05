package com.mypfeproject.pfe.repository;

import com.mypfeproject.pfe.entities.Notification;
import com.mypfeproject.pfe.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByCollaborateur(User collaborateur);
    List<Notification> findByIsReadFalse();
    Notification findByDemandeId(Long demandeId);



}

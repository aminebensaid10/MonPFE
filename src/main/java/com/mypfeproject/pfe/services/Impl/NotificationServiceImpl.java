package com.mypfeproject.pfe.services.Impl;

import com.mypfeproject.pfe.entities.Notification;
import com.mypfeproject.pfe.repository.NotificationRepository;
import com.mypfeproject.pfe.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    @Override
    public void creerNotification(Notification notification) {
        notificationRepository.save(notification);
    }
}

package com.mypfeproject.pfe.services;

import com.mypfeproject.pfe.entities.SituationFamiliale;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Map;

public interface UserService {
UserDetailsService userDetailsService();
    public Map<SituationFamiliale, Long> getUsersByFamilySituation();
}

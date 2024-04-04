package com.mypfeproject.pfe.services.Impl;

import com.mypfeproject.pfe.entities.SituationFamiliale;
import com.mypfeproject.pfe.entities.User;
import com.mypfeproject.pfe.repository.UserRepository;
import com.mypfeproject.pfe.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("user not found"));
            }
        };
    }
    public Map<SituationFamiliale, Long> getUsersByFamilySituation() {
        List<User> users = userRepository.findAll();

        Map<SituationFamiliale, Long> usersBySituation = new HashMap<>();

        for (User user : users) {
            SituationFamiliale situationFamiliale = user.getSituationFamiliale();
            if (situationFamiliale != null) {
                usersBySituation.put(situationFamiliale, usersBySituation.getOrDefault(situationFamiliale, 0L) + 1);
            }
        }

        usersBySituation.entrySet().removeIf(entry -> entry.getKey() == null);

        return usersBySituation;
    }
}

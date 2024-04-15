package com.mypfeproject.pfe.services.Impl;

import com.mypfeproject.pfe.entities.MembreFamille;
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
import java.util.stream.Collectors;

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
    public Map<String, Long> getFamilyMembersStatistics() {
        List<User> users = userRepository.findAll();

        Map<Integer, Long> familyMembersCounts = users.stream()
                .collect(Collectors.groupingBy(
                        this::countFamilyMembers,
                        Collectors.counting()
                ));

        Map<String, Long> formattedStatistics = new HashMap<>();

        for (Map.Entry<Integer, Long> entry : familyMembersCounts.entrySet()) {
            int familyMembersCount = entry.getKey();
            long count = entry.getValue();

            String usersLabel = familyMembersCount == 1 ? "Collaborateur" : "Collaborateurs";
            String membersLabel = familyMembersCount == 1 ? "membre de famille" : "membres de famille";

            String description = String.format("%d %s : %d %s", familyMembersCount, usersLabel, count, membersLabel);
            formattedStatistics.put(description, count);
        }

        return formattedStatistics;
    }

    private int countFamilyMembers(User user) {
        List<MembreFamille> membresFamille = user.getMembresFamille();
        return membresFamille.size();
    }
}

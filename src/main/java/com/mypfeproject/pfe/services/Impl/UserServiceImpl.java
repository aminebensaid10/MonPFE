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
    public Map<String, Long> countUsersByTransportMode() {
        List<User> users = userRepository.findAll();
        Map<String, Long> usersByTransportMode = new HashMap<>();

        for (User user : users) {
            String transportMode = user.getModeDeTransport();
            if (transportMode != null && !transportMode.isEmpty()) {
                usersByTransportMode.put(transportMode, usersByTransportMode.getOrDefault(transportMode, 0L) + 1);
            }
        }

        return usersByTransportMode;
    }
    public Map<String, Double> getUsersPercentageByTransportMode() {
        Map<String, Long> usersByTransportMode = countUsersByTransportMode();
        long totalUsers = usersByTransportMode.values().stream().mapToLong(Long::longValue).sum();

        Map<String, Double> percentageByTransportMode = new HashMap<>();
        for (Map.Entry<String, Long> entry : usersByTransportMode.entrySet()) {
            String transportMode = entry.getKey();
            Long count = entry.getValue();
            double percentage = (count * 100.0) / totalUsers;
            percentageByTransportMode.put(transportMode, percentage);
        }

        return percentageByTransportMode;
    }
    public Map<SituationFamiliale, Double> getUsersPercentageBySituationFamiliale() {
        List<User> users = userRepository.findAll(); // Suppose userRepository est injecté

        // Filtrer les utilisateurs ayant une situation familiale non nulle
        List<User> usersWithSituationFamiliale = users.stream()
                .filter(user -> user.getSituationFamiliale() != null)
                .collect(Collectors.toList());

        // Compter le nombre d'utilisateurs pour chaque situation familiale
        Map<SituationFamiliale, Long> usersBySituationFamiliale = usersWithSituationFamiliale.stream()
                .collect(Collectors.groupingBy(
                        User::getSituationFamiliale,
                        Collectors.counting()));

        long totalUsers = usersWithSituationFamiliale.size();

        // Calculer les pourcentages pour chaque situation familiale
        Map<SituationFamiliale, Double> percentageBySituationFamiliale = new HashMap<>();
        for (Map.Entry<SituationFamiliale, Long> entry : usersBySituationFamiliale.entrySet()) {
            SituationFamiliale situationFamiliale = entry.getKey();
            Long count = entry.getValue();

            // Calculer le pourcentage
            double percentage = (count * 100.0) / totalUsers;

            // Ajouter le pourcentage à la map
            percentageBySituationFamiliale.put(situationFamiliale, percentage);
        }

        return percentageBySituationFamiliale;
    }
}

package com.mypfeproject.pfe.repository;

import com.mypfeproject.pfe.entities.Role;
import com.mypfeproject.pfe.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
Optional<User> findByEmail(String email);
User findByRole(Role role);
}

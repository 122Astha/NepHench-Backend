package com.example.NepHench.repository;

import com.example.NepHench.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    List<User> findAllByRoleId(Integer role_id);

    Optional<User> findByUsernameOrEmail(String usernameOrEmail, String usernameOrEmail1);

    User findByVerificationToken(String verificationToken);
}

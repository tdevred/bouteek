package com.tdevred.bouteek.authentication.repositories;

import com.tdevred.bouteek.authentication.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    User findByFullName(String name);
}

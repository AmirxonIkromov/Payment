package com.amirscode.payment.repository;

import com.amirscode.payment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}

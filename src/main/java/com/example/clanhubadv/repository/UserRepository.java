package com.example.clanhubadv.repository;

import com.example.clanhubadv.entity.Role;
import com.example.clanhubadv.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

//     Optional<User> findById(UUID id);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    List<User> findByRole(Role role);

    boolean existsByEmail(String email);
}

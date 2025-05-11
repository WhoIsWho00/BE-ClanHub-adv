package com.example.clanhubadv.repository;

import com.example.clanhubadv.entity.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FamilyRepository extends JpaRepository<Family, UUID> {
    Optional<Family> findByName(String name);
    Optional<Family> findByInviteCode(UUID inviteCode);
    boolean existsByName(String name);
}
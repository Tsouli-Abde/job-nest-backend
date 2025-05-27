package com.dauphine.jobnest.repositories;

import com.dauphine.jobnest.models.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ApplicantRepository extends JpaRepository<Applicant, UUID> {

    Optional<Applicant> findByUsername(String username);
}

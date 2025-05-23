package com.dauphine.jobnest.repositories;

import com.dauphine.jobnest.models.Application;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, UUID> {
    List<Application> findByApplicantId(UUID applicantId);
}

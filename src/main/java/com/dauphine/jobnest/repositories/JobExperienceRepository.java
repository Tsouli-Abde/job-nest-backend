package com.dauphine.jobnest.repositories;

import com.dauphine.jobnest.models.JobExperience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobExperienceRepository extends JpaRepository<JobExperience, Long> {
    List<JobExperience> findByApplicantId(java.util.UUID applicantId);
}
package com.dauphine.jobnest.services;

import com.dauphine.jobnest.models.JobExperience;
import com.dauphine.jobnest.repositories.JobExperienceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class JobExperienceService {

    private final JobExperienceRepository jobExperienceRepository;

    public JobExperienceService(JobExperienceRepository jobExperienceRepository) {
        this.jobExperienceRepository = jobExperienceRepository;
    }

    public JobExperience save(JobExperience experience) {
        return jobExperienceRepository.save(experience);
    }

    public List<JobExperience> getAll() {
        return jobExperienceRepository.findAll();
    }

    public Optional<JobExperience> getById(Long id) {
        return jobExperienceRepository.findById(id);
    }

    public List<JobExperience> getByApplicantId(UUID applicantId) {
        return jobExperienceRepository.findByApplicantId(applicantId);
    }
}
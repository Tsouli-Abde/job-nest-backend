package com.dauphine.jobnest.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.dauphine.jobnest.models.Application;
import com.dauphine.jobnest.repositories.ApplicationRepository;

@Service
public class ApplicationService {
    private final ApplicationRepository applicationRepository;

    public ApplicationService(ApplicationRepository repository) {
        this.applicationRepository = repository;
    }

    public Application save(Application application) {
        return applicationRepository.save(application);
    }

    public List<Application> getByApplicantId(UUID applicantId) {
        return applicationRepository.findByApplicantId(applicantId);
    }

    public boolean existsByApplicantIdAndJobId(UUID applicantId, UUID jobId) {
        return applicationRepository.existsByApplicantIdAndJobId(applicantId, jobId);
    }
    public Optional<Application> getById(UUID id) {
        return applicationRepository.findById(id);
    }

    public List<Application> getByJobId(UUID jobId) {
        return applicationRepository.findByJobId(jobId);
    }
}

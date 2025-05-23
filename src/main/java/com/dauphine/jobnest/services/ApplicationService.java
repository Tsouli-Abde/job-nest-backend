package com.dauphine.jobnest.services;

import java.util.List;
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
}

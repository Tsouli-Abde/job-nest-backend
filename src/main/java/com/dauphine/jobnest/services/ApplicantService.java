package com.dauphine.jobnest.services;

import com.dauphine.jobnest.models.Applicant;
import com.dauphine.jobnest.repositories.ApplicantRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class ApplicantService {

    private final ApplicantRepository applicantRepository;

    public ApplicantService(ApplicantRepository applicantRepository) {
        this.applicantRepository = applicantRepository;
    }

    public List<Applicant> getAllApplicants() {
        return applicantRepository.findAll();
    }

    public Applicant save(Applicant applicant) {
        return applicantRepository.save(applicant);
    }

    public Applicant findById(UUID id) {
        return applicantRepository.findById(id).orElse(null);
    }

    public Applicant findByUsername(String username) {
        return applicantRepository.findByUsername(username);
    }
}


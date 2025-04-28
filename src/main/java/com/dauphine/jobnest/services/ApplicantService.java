package com.dauphine.jobnest.services;

import com.dauphine.jobnest.models.Applicant;
import com.dauphine.jobnest.repositories.ApplicantRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ApplicantService {

    private final ApplicantRepository applicantRepository;

    public ApplicantService(ApplicantRepository applicantRepository) {
        this.applicantRepository = applicantRepository;
    }

    public List<Applicant> getAllApplicants() {
        return applicantRepository.findAll();
    }
}


package com.dauphine.jobnest.controllers;

import com.dauphine.jobnest.models.Applicant;
import com.dauphine.jobnest.services.ApplicantService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/applicants")
@CrossOrigin
public class ApplicantController {

    private final ApplicantService applicantService;

    public ApplicantController(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

    @GetMapping
    public List<Applicant> getAllApplicants() {
        return applicantService.getAllApplicants();
    }
}

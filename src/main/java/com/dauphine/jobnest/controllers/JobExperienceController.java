package com.dauphine.jobnest.controllers;

import com.dauphine.jobnest.models.Applicant;
import com.dauphine.jobnest.models.JobExperience;
import com.dauphine.jobnest.services.ApplicantService;
import com.dauphine.jobnest.services.JobExperienceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/job-experiences")
public class JobExperienceController {

    private final JobExperienceService jobExperienceService;
    private final ApplicantService applicantService;

    public JobExperienceController(JobExperienceService jobExperienceService, ApplicantService applicantService) {
        this.jobExperienceService = jobExperienceService;
        this.applicantService = applicantService;
    }

    @PostMapping("/applicant/{applicantId}")
    public ResponseEntity<?> addExperienceToApplicant(@PathVariable UUID applicantId, @RequestBody JobExperience experience) {
        Applicant applicant = applicantService.findById(applicantId);
        if (applicant == null) {
            return ResponseEntity.badRequest().body("Applicant not found");
        }

        experience.setApplicant(applicant);
        return ResponseEntity.ok(jobExperienceService.save(experience));
    }

    @GetMapping
    public ResponseEntity<List<JobExperience>> getAllExperiences() {
        return ResponseEntity.ok(jobExperienceService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getExperienceById(@PathVariable UUID id) {
        return jobExperienceService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/applicant/{applicantId}")
    public ResponseEntity<List<JobExperience>> getExperiencesByApplicant(@PathVariable UUID applicantId) {
        return ResponseEntity.ok(jobExperienceService.getByApplicantId(applicantId));
    }
}
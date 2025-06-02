package com.dauphine.jobnest.controllers;

import com.dauphine.jobnest.dto.JobExperienceRequest;
import com.dauphine.jobnest.models.Applicant;
import com.dauphine.jobnest.models.JobExperience;
import com.dauphine.jobnest.services.ApplicantService;
import com.dauphine.jobnest.services.JobExperienceService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.dauphine.jobnest.dto.JobExperienceUpdate;

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
    @Operation(
        summary = "Add a job experience to an applicant", 
        description = "Creates a new job experience and assigns it to the specified applicant"    
    )
    public ResponseEntity<?> addExperienceToApplicant(
            @PathVariable UUID applicantId,
            @RequestBody JobExperienceRequest experienceDto) {

        Applicant applicant = applicantService.findById(applicantId);
        if (applicant == null) {
            return ResponseEntity.badRequest().body("Applicant not found");
        }

        JobExperience experience = new JobExperience();
        experience.setApplicant(applicant);
        experience.setCompanyName(experienceDto.getCompanyName());
        experience.setPosition(experienceDto.getPosition());
        experience.setStartDate(experienceDto.getStartDate());
        experience.setEndDate(experienceDto.getEndDate());
        experience.setDescription(experienceDto.getDescription());

        return ResponseEntity.ok(jobExperienceService.save(experience));
    }

    @GetMapping
    @Operation(
        summary = "Retrieve all job experiences",
        description = "Returns a list of all job experiences in the system"
    )
    public ResponseEntity<List<JobExperience>> getAllExperiences() {
        return ResponseEntity.ok(jobExperienceService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Retrieve a job experience by ID",
        description = "Returns a single job experience based on its unique identifier"
    )
    public ResponseEntity<?> getExperienceById(@PathVariable UUID id) {
        return jobExperienceService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/applicant/{applicantId}")
    @Operation(
        summary = "Get experiences by applicant ID",
        description = "Retrieves all job experiences for the specified applicant"
    )
    public ResponseEntity<List<JobExperience>> getExperiencesByApplicant(@PathVariable UUID applicantId) {
        return ResponseEntity.ok(jobExperienceService.getByApplicantId(applicantId));
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Update an existing job experience",
        description = "Updates the details of a job experience with the specified ID"
    )
    public ResponseEntity<?> updateExperience(@PathVariable UUID id, @RequestBody JobExperienceUpdate updated) {
        return jobExperienceService.getById(id)
                .map(existing -> {
                    existing.setCompanyName(updated.getCompanyName());
                    existing.setPosition(updated.getPosition());
                    existing.setStartDate(updated.getStartDate());
                    existing.setEndDate(updated.getEndDate());
                    existing.setDescription(updated.getDescription());
                    return ResponseEntity.ok(jobExperienceService.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete a job experience",
        description = "Deletes a job experience based on its ID"
    )
    public ResponseEntity<?> deleteExperience(@PathVariable UUID id) {
        return jobExperienceService.getById(id)
                .map(exp -> {
                    jobExperienceService.deleteById(id);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
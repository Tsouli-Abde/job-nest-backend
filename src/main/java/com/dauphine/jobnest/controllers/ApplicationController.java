package com.dauphine.jobnest.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.dauphine.jobnest.models.ApplicationStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dauphine.jobnest.dto.ApplicationRequest;
import com.dauphine.jobnest.models.Applicant;
import com.dauphine.jobnest.models.Application;
import com.dauphine.jobnest.models.Job;
import com.dauphine.jobnest.services.ApplicantService;
import com.dauphine.jobnest.services.ApplicationService;
import com.dauphine.jobnest.services.JobService;

@RestController
@RequestMapping("/api/v1/applications")
@CrossOrigin
public class ApplicationController {

    private final ApplicationService applicationService;
    private final ApplicantService applicantService;
    private final JobService jobService;

    public ApplicationController(ApplicationService applicationService, ApplicantService applicantService,
            JobService jobService) {
        this.applicationService = applicationService;
        this.applicantService = applicantService;
        this.jobService = jobService;
    }

    @PostMapping
    public ResponseEntity<Application> createApplication(@RequestBody ApplicationRequest request) {
        Applicant applicant = applicantService.findById(request.applicantId);
        Job job = jobService.getJobById(request.jobId).orElseThrow();

        Application app = new Application();
        app.setApplicant(applicant);
        app.setJob(job);
        app.setCoverLetter(request.coverLetter);
        app.setApplicationDate(LocalDateTime.now());

        return ResponseEntity.ok(applicationService.save(app));
    }

    @GetMapping("/applicant/{applicantId}")
    public ResponseEntity<List<Application>> getByApplicant(@PathVariable UUID applicantId) {
        List<Application> applications = applicationService.getByApplicantId(applicantId);
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/has-applied")
    public ResponseEntity<Boolean> hasAlreadyApplied(
            @RequestParam UUID applicantId,
            @RequestParam UUID jobId) {
        boolean exists = applicationService.existsByApplicantIdAndJobId(applicantId, jobId);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Application> getApplicationById(@PathVariable UUID id) {
        return applicationService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<Application>> getByJob(@PathVariable UUID jobId) {
        List<Application> applications = applicationService.getByJobId(jobId);
        return ResponseEntity.ok(applications);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Application> updateStatus(@PathVariable UUID id, @RequestParam ApplicationStatus status) {
        Application application = applicationService.getById(id).orElse(null);
        if (application == null) return ResponseEntity.notFound().build();

        application.setStatus(status);
        applicationService.save(application);
        return ResponseEntity.ok(application);
    }
}

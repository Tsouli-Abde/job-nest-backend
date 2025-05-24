package com.dauphine.jobnest.controllers;

import com.dauphine.jobnest.dto.JobRequest;
import com.dauphine.jobnest.dto.JobResponse;
import com.dauphine.jobnest.models.Company;
import com.dauphine.jobnest.models.Job;
import com.dauphine.jobnest.services.CompanyService;
import com.dauphine.jobnest.services.JobService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/jobs")
@CrossOrigin
public class JobController {

    private final JobService jobService;
    private final CompanyService companyService;

    public JobController(JobService jobService, CompanyService companyService) {
        this.jobService = jobService;
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<List<JobResponse>> getAllJobs() {
        List<Job> jobs = jobService.getAllJobs();
        List<JobResponse> response = jobs.stream()
                .map(JobResponse::new)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Job> createJob(@RequestBody JobRequest request) {
        Company company = companyService.findById(request.companyId);
        if (company == null) {
            return ResponseEntity.badRequest().build();
        }

        Job job = new Job();
        job.setTitle(request.title);
        job.setDescription(request.description);
        job.setResponsibilities(request.responsibilities);
        job.setQualifications(request.qualifications);
        job.setLocation(request.location);
        job.setSalaryMin(request.salaryMin);
        job.setSalaryMax(request.salaryMax);
        job.setType(request.type);
        job.setExperienceLevel(request.experienceLevel);
        job.setPostedAt(LocalDateTime.now());
        job.setCompany(company);

        Job savedJob = jobService.createJob(job);
        return ResponseEntity.ok(savedJob);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Job>> searchJobs(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String experienceLevel,
            @RequestParam(required = false) Integer salaryMin,
            @RequestParam(required = false) Integer salaryMax) {
        List<Job> results = jobService.searchJobs(location, type, experienceLevel, salaryMin, salaryMax);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable UUID id) {
        return jobService.getJobById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<Job>> getJobsByCompany(@PathVariable UUID companyId) {
        List<Job> jobs = jobService.getJobsByCompanyId(companyId);
        return ResponseEntity.ok(jobs);
    }
}
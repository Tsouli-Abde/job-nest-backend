package com.dauphine.jobnest.controllers;

import com.dauphine.jobnest.dto.JobRequest;
import com.dauphine.jobnest.dto.JobResponse;
import com.dauphine.jobnest.models.Company;
import com.dauphine.jobnest.models.Job;
import com.dauphine.jobnest.services.CompanyService;
import com.dauphine.jobnest.services.JobService;

import io.swagger.v3.oas.annotations.Operation;

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
    @Operation(
        summary = "Get all job listings",
        description = "Returns a list of all available jobs with detailed information"
    )
    public ResponseEntity<List<JobResponse>> getAllJobs() {
        List<Job> jobs = jobService.getAllJobs();
        List<JobResponse> response = jobs.stream()
                .map(JobResponse::new)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(
        summary = "Create a new job listing",
        description = "Allows a company to create and post a new job offer"
    )
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
    @Operation(
        summary = "Search jobs with filters",
        description = "Retrieves jobs that match specified filtering criteria such as location, type, salary range, etc."
    )
    public ResponseEntity<List<JobResponse>> searchJobs(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String experienceLevel,
            @RequestParam(required = false) Integer salaryMin,
            @RequestParam(required = false) Integer salaryMax) {

        List<Job> jobs = jobService.searchJobs(location, type, experienceLevel, salaryMin, salaryMax);
        List<JobResponse> jobResponses = jobs.stream()
                .map(JobResponse::new)
                .toList();
        return ResponseEntity.ok(jobResponses);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Get job by ID",
        description = "Retrieves detailed information about a specific job offer"
    )
    public ResponseEntity<JobResponse> getJobById(@PathVariable UUID id) {
        return jobService.getJobById(id)
                .map(job -> ResponseEntity.ok(new JobResponse(job)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/company/{companyId}")
    @Operation(
        summary = "Get jobs by company",
        description = "Lists all jobs posted by a specific company"
    )
    public ResponseEntity<List<Job>> getJobsByCompany(@PathVariable UUID companyId) {
        List<Job> jobs = jobService.getJobsByCompanyId(companyId);
        return ResponseEntity.ok(jobs);
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete a job posting",
        description = "Removes a job posting based on its unique ID"
    )
    public ResponseEntity<Void> deleteJob(@PathVariable UUID id) {
        jobService.deleteJobById(id);
        return ResponseEntity.noContent().build();
    }
}
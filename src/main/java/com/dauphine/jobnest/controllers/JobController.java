package com.dauphine.jobnest.controllers;

import com.dauphine.jobnest.models.Job;
import com.dauphine.jobnest.services.JobService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/jobs")
@CrossOrigin
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public List<Job> getAllJobs() {
        return jobService.getAllJobs();
    }

    @PostMapping
    public ResponseEntity<Job> createJob(@RequestBody Job job) {
        job.setPostedAt(LocalDateTime.now());
        Job savedJob = jobService.createJob(job);
        return ResponseEntity.ok(savedJob);
    }
}
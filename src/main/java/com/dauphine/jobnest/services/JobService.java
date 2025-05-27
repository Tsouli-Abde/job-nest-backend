package com.dauphine.jobnest.services;

import com.dauphine.jobnest.models.Job;
import com.dauphine.jobnest.repositories.JobRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class JobService {

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public Job createJob(Job job) {
        job.setPostedAt(LocalDateTime.now());
        return jobRepository.save(job);
    }

    public Optional<Job> getJobById(UUID id) {
        return jobRepository.findById(id);
    }

    public List<Job> searchJobs(String location, String type, String experienceLevel, Integer salaryMin,
            Integer salaryMax) {
        return jobRepository.findByFilters(location, type, experienceLevel, salaryMin, salaryMax);
    }

    public List<Job> getJobsByCompanyId(UUID companyId) {
        return jobRepository.findByCompanyId(companyId);
    }
    
    public void deleteJobById(UUID id) {
        jobRepository.deleteById(id);
    }
}

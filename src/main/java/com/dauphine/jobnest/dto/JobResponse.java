package com.dauphine.jobnest.dto;

import com.dauphine.jobnest.models.Job;
import java.time.LocalDateTime;
import java.util.UUID;

public class JobResponse {
    public UUID id;
    public String title;
    public String description;
    public String responsibilities;
    public String qualifications;
    public String location;
    public Integer salaryMin;
    public Integer salaryMax;
    public String type;
    public String experienceLevel;
    public LocalDateTime postedAt;
    public UUID companyId;
    public int numberOfApplicants;

    public JobResponse(Job job) {
        this.id = job.getId();
        this.title = job.getTitle();
        this.description = job.getDescription();
        this.responsibilities = job.getResponsibilities();
        this.qualifications = job.getQualifications();
        this.location = job.getLocation();
        this.salaryMin = job.getSalaryMin();
        this.salaryMax = job.getSalaryMax();
        this.type = job.getType();
        this.experienceLevel = job.getExperienceLevel();
        this.postedAt = job.getPostedAt();
        this.companyId = job.getCompany() != null ? job.getCompany().getId() : null;
        this.numberOfApplicants = job.getApplications() != null ? job.getApplications().size() : 0;
    }
}
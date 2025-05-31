package com.dauphine.jobnest.dto;

import com.dauphine.jobnest.models.Job;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class JobResponse {
    private UUID id;
    private String title;
    private String description;
    private String responsibilities;
    private String qualifications;
    private String location;
    private Integer salaryMin;
    private Integer salaryMax;
    private String type;
    private String experienceLevel;
    private LocalDateTime postedAt;
    private UUID companyId;
    private String companyName;

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

        if (job.getCompany() != null) {
            this.companyName = job.getCompany().getCompanyName();
            this.companyId = job.getCompany().getId();
        } else {
            this.companyName = "Unknown Company";
        }
    }
}
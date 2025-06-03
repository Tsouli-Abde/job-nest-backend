package com.dauphine.jobnest.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.dauphine.jobnest.models.Application;

import lombok.Data;

@Data
public class ApplicationResponse {
    private UUID id;
    private String coverLetter;
    private LocalDateTime applicationDate;
    private String status;

    private UUID applicantId;
    private String applicantFirstName;
    private String applicantLastName;

    private JobResponse job;

    public ApplicationResponse(Application application) {
        this.id = application.getId();
        this.coverLetter = application.getCoverLetter();
        this.applicationDate = application.getApplicationDate();
        this.status = application.getStatus().name();

        this.applicantId = application.getApplicant().getId();
        this.applicantFirstName = application.getApplicant().getFirstName();
        this.applicantLastName = application.getApplicant().getLastName();

        this.job = new JobResponse(application.getJob());
    }
}

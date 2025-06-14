package com.dauphine.jobnest.dto;

import java.time.LocalDate;

public class JobExperienceRequest {
    public String companyName;
    public String position;
    public String description;
    public LocalDate startDate;
    public LocalDate endDate;

    public String getCompanyName() {
        return companyName;
    }

    public String getPosition() {
        return position;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
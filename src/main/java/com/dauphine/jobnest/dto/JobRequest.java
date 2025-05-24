package com.dauphine.jobnest.dto;

import java.util.UUID;

public class JobRequest {
    public String title;
    public String description;
    public String responsibilities;
    public String qualifications;
    public String location;
    public Integer salaryMin;
    public Integer salaryMax;
    public String type;
    public String experienceLevel;
    public UUID companyId;
}
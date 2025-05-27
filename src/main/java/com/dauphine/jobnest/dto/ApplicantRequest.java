package com.dauphine.jobnest.dto;

import java.util.List;

public class ApplicantRequest {
    public String firstName;
    public String lastName;
    public String email;
    public String phoneNumber;
    public String skills;
    public String username;
    public String password;
    public List<JobExperienceRequest> experiences;
}
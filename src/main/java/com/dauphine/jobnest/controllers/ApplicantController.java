package com.dauphine.jobnest.controllers;

import com.dauphine.jobnest.dto.ApplicantRequest;
import com.dauphine.jobnest.dto.ApplicantUpdate;
import com.dauphine.jobnest.dto.JobExperienceRequest;
import com.dauphine.jobnest.dto.LoginRequest;
import com.dauphine.jobnest.models.Applicant;
import com.dauphine.jobnest.models.JobExperience;
import com.dauphine.jobnest.services.ApplicantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/applicants")
public class ApplicantController {

    private final ApplicantService applicantService;
    private final PasswordEncoder passwordEncoder;

    public ApplicantController(ApplicantService applicantService, PasswordEncoder passwordEncoder) {
        this.applicantService = applicantService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public List<Applicant> getAllApplicants() {
        return applicantService.getAllApplicants();
    }

    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Applicant> register(@RequestBody ApplicantRequest request) {
        System.out.println("Received applicant: " + request.username);

        Applicant applicant = new Applicant();
        applicant.setFirstName(request.firstName);
        applicant.setLastName(request.lastName);
        applicant.setEmail(request.email);
        applicant.setPhoneNumber(request.phoneNumber);
        applicant.setSkills(request.skills);
        applicant.setUsername(request.username);
        applicant.setPassword(passwordEncoder.encode(request.password));

        if (request.experiences != null) {
            List<JobExperience> experienceList = new ArrayList<>();
            for (JobExperienceRequest exp : request.experiences) {
                JobExperience jobExperience = new JobExperience();
                jobExperience.setCompanyName(exp.companyName);
                jobExperience.setPosition(exp.position);
                jobExperience.setDescription(exp.description);
                jobExperience.setStartDate(exp.startDate);
                jobExperience.setEndDate(exp.endDate);
                jobExperience.setApplicant(applicant);
                experienceList.add(jobExperience);
            }
            applicant.setExperiences(experienceList);
        }

        return ResponseEntity.ok(applicantService.save(applicant));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Applicant applicant = applicantService.findByUsername(request.getUsername());
        if (applicant != null && passwordEncoder.matches(request.getPassword(), applicant.getPassword())) {
            return ResponseEntity.ok(applicant);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<Applicant> updateApplicant(@PathVariable UUID id, @RequestBody ApplicantUpdate dto) {
        Applicant existing = applicantService.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        if (dto.firstName != null) existing.setFirstName(dto.firstName);
        if (dto.lastName != null) existing.setLastName(dto.lastName);
        if (dto.email != null) existing.setEmail(dto.email);
        if (dto.phoneNumber != null) existing.setPhoneNumber(dto.phoneNumber);
        if (dto.skills != null) existing.setSkills(dto.skills);
        if (dto.username != null) existing.setUsername(dto.username);
        if (dto.password != null) existing.setPassword(dto.password);

        return ResponseEntity.ok(applicantService.save(existing));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Applicant> getApplicantById(@PathVariable UUID id) {
        Applicant applicant = applicantService.findById(id);
        if (applicant == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(applicant);
    }
}

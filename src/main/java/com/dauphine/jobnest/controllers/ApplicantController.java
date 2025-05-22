package com.dauphine.jobnest.controllers;

import com.dauphine.jobnest.dto.LoginRequest;
import com.dauphine.jobnest.models.Applicant;
import com.dauphine.jobnest.services.ApplicantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/register")
    public ResponseEntity<Applicant> register(@RequestBody Applicant applicant) {
        System.out.println("Received applicant: " + applicant.getUsername());
        System.out.println("Password before encode: " + applicant.getPassword());

        applicant.setPassword(passwordEncoder.encode(applicant.getPassword()));
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

    @PutMapping("/{id}")
    public ResponseEntity<Applicant> updateApplicant(@PathVariable UUID id, @RequestBody Applicant updatedApplicant) {
        Applicant existing = applicantService.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        if (updatedApplicant.getFirstName() != null)
            existing.setFirstName(updatedApplicant.getFirstName());
        if (updatedApplicant.getLastName() != null)
            existing.setLastName(updatedApplicant.getLastName());
        if (updatedApplicant.getEmail() != null)
            existing.setEmail(updatedApplicant.getEmail());
        if (updatedApplicant.getPhoneNumber() != null)
            existing.setPhoneNumber(updatedApplicant.getPhoneNumber());
        if (updatedApplicant.getSkills() != null)
            existing.setSkills(updatedApplicant.getSkills());
        if (updatedApplicant.getUsername() != null)
            existing.setUsername(updatedApplicant.getUsername());

        return ResponseEntity.ok(applicantService.save(existing));
    }

}

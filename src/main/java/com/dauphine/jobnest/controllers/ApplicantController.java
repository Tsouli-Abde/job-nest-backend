package com.dauphine.jobnest.controllers;

import com.dauphine.jobnest.dto.LoginRequest;
import com.dauphine.jobnest.models.Applicant;
import com.dauphine.jobnest.services.ApplicantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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

}

package com.dauphine.jobnest.controllers;

import com.dauphine.jobnest.dto.CompanyRequest;
import com.dauphine.jobnest.dto.CompanyUpdate;
import com.dauphine.jobnest.dto.LoginRequest;
import com.dauphine.jobnest.models.Company;
import com.dauphine.jobnest.services.CompanyService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.dauphine.jobnest.models.Job;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/companies")
public class CompanyController {

    private final CompanyService companyService;
    private final PasswordEncoder passwordEncoder;

    public CompanyController(CompanyService companyService, PasswordEncoder passwordEncoder) {
        this.companyService = companyService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    @Operation(
        summary = "Get all companies",
        description = "Returns a list of all registered companies"
    )
    public List<Company> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @PostMapping("/register")
    @Operation(
        summary = "Register a new company",
        description = "Creates a new company account based on the provided information"
    )
    public ResponseEntity<Company> register(@RequestBody CompanyRequest request) {
        Company company = new Company();
        company.setCompanyName(request.companyName);
        company.setEmail(request.email);
        company.setPhoneNumber(request.phoneNumber);
        company.setIndustry(request.industry);
        company.setWebsite(request.website);
        company.setUsername(request.username);
        company.setPassword(passwordEncoder.encode(request.password));
        return ResponseEntity.ok(companyService.save(company));
    }

    @PostMapping("/login")
    @Operation(
        summary = "Authenticate a company",
        description = "Logs in a company using its username and password"
    )
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Company company = companyService.findByUsername(request.getUsername());
        if (company != null && passwordEncoder.matches(request.getPassword(), company.getPassword())) {
            return ResponseEntity.ok(company);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    @Operation(
        summary = "Update company profile",
        description = "Updates the profile of a company identified by its ID"
    )
    public ResponseEntity<Company> updateCompany(@PathVariable UUID id, @RequestBody CompanyUpdate dto) {
        Company existing = companyService.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        if (dto.companyName != null)
            existing.setCompanyName(dto.companyName);
        if (dto.email != null)
            existing.setEmail(dto.email);
        if (dto.phoneNumber != null)
            existing.setPhoneNumber(dto.phoneNumber);
        if (dto.industry != null)
            existing.setIndustry(dto.industry);
        if (dto.website != null)
            existing.setWebsite(dto.website);
        if (dto.username != null)
            existing.setUsername(dto.username);
        if (dto.password != null)
            existing.setPassword(dto.password);

        return ResponseEntity.ok(companyService.save(existing));
    }

    @GetMapping("/{companyId}/jobs")
    @Operation(
        summary = "Get jobs by company ID",
        description = "Returns a list of jobs associated with the specified company"
    )
    public ResponseEntity<List<Job>> getCompanyJobs(@PathVariable UUID companyId) {
        Company company = companyService.findById(companyId);
        if (company == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(company.getJobs());
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Get company by ID",
        description = "Retrieves the company details using its unique identifier"
    )
    public ResponseEntity<Company> getCompanyById(@PathVariable UUID id) {
        Company company = companyService.findById(id);
        if (company == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(company);
    }

    @GetMapping("/check-username")
    public ResponseEntity<Boolean> checkUsername(@RequestParam String username) {
        Company existing = companyService.findByUsername(username);
        return ResponseEntity.ok(existing == null);
    }
}

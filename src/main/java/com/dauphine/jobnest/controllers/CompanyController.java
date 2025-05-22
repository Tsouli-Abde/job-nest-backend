package com.dauphine.jobnest.controllers;

import com.dauphine.jobnest.dto.LoginRequest;
import com.dauphine.jobnest.models.Company;
import com.dauphine.jobnest.services.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
    public List<Company> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @PostMapping("/register")
    public ResponseEntity<Company> register(@RequestBody Company company) {
        company.setPassword(passwordEncoder.encode(company.getPassword()));
        return ResponseEntity.ok(companyService.save(company));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Company company = companyService.findByUsername(request.getUsername());
        if (company != null && passwordEncoder.matches(request.getPassword(), company.getPassword())) {
            return ResponseEntity.ok(company);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable UUID id, @RequestBody Company updatedCompany) {
        Company existing = companyService.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        existing.setCompanyName(updatedCompany.getCompanyName());
        existing.setEmail(updatedCompany.getEmail());
        existing.setPhoneNumber(updatedCompany.getPhoneNumber());
        existing.setIndustry(updatedCompany.getIndustry());

        return ResponseEntity.ok(companyService.save(existing));
    }
}
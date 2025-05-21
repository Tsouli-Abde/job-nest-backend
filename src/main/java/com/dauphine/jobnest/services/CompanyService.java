package com.dauphine.jobnest.services;

import com.dauphine.jobnest.models.Company;
import com.dauphine.jobnest.repositories.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Company save(Company company) {
        return companyRepository.save(company);
    }

    public Company findByUsername(String username) {
        return companyRepository.findByUsername(username);
    }

    public Company findById(UUID id) {
        return companyRepository.findById(id).orElse(null);
    }
}
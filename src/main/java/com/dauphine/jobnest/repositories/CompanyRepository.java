package com.dauphine.jobnest.repositories;

import com.dauphine.jobnest.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
    Company findByUsername(String username);
}
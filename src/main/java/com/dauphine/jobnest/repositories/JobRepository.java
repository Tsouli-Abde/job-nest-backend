package com.dauphine.jobnest.repositories;

import com.dauphine.jobnest.models.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JobRepository extends JpaRepository<Job, UUID> {
}

package com.dauphine.jobnest.repositories;

import com.dauphine.jobnest.models.Application;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, UUID> {
}

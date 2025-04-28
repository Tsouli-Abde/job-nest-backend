package com.dauphine.jobnest.repositories;

import com.dauphine.jobnest.models.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}

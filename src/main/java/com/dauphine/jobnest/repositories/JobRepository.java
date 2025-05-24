package com.dauphine.jobnest.repositories;

import com.dauphine.jobnest.models.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface JobRepository extends JpaRepository<Job, UUID> {
    @Query("SELECT j FROM Job j WHERE " +
            "(:location IS NULL OR j.location LIKE %:location%) AND " +
            "(:type IS NULL OR j.type = :type) AND " +
            "(:experienceLevel IS NULL OR j.experienceLevel = :experienceLevel) AND " +
            "(:salaryMin IS NULL OR j.salaryMin >= :salaryMin) AND " +
            "(:salaryMax IS NULL OR j.salaryMax <= :salaryMax) " +
            "ORDER BY j.postedAt DESC")
    List<Job> findByFilters(
            @Param("location") String location,
            @Param("type") String type,
            @Param("experienceLevel") String experienceLevel,
            @Param("salaryMin") Integer salaryMin,
            @Param("salaryMax") Integer salaryMax);

    List<Job> findByCompanyId(UUID companyId);
}

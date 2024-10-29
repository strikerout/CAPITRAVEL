package com.capitravel.Capitravel.repository;

import com.capitravel.Capitravel.model.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    boolean existsByTitle(String title);

    @Query("SELECT e FROM Experience e JOIN e.categories c WHERE c.id = :categoryId")
    List<Experience> findByCategoryId(@Param("categoryId") Long categoryId);

    @Query("SELECT e FROM Experience e JOIN e.properties p WHERE p.id = :propertyId")
    List<Experience> findByPropertyId(@Param("propertyId") Long propertyId);


}
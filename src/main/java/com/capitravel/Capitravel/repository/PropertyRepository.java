package com.capitravel.Capitravel.repository;

import com.capitravel.Capitravel.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Long> {
}

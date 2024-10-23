package com.capitravel.Capitravel.repository;

import com.capitravel.Capitravel.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

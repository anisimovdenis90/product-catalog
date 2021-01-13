package com.anisimov.denis.repositories;

import com.anisimov.denis.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @Modifying
    @Query("UPDATE Product p SET p.description = ?1 WHERE p.id = ?2")
    void updateDescriptionById(String description, Long id);
}

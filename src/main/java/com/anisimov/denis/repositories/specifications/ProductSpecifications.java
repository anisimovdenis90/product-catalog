package com.anisimov.denis.repositories.specifications;

import com.anisimov.denis.entities.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecifications {

    public static Specification<Product> nameLike(String namePart) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), String.format("%%%s%%", namePart));
    }
}

package com.anisimov.denis.services;

import com.anisimov.denis.entities.Product;
import com.anisimov.denis.exceptions.CatalogError;
import com.anisimov.denis.repositories.ProductRepository;
import com.anisimov.denis.repositories.specifications.ProductSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Product> findAllProducts(String name, int page, int size) {
        Specification<Product> spec = Specification.where(null);
        if (name != null && !name.isBlank()) {
            spec = spec.and(ProductSpecifications.nameLike(name));
        }
        return productRepository.findAll(spec, PageRequest.of(page, size));
    }

    public ResponseEntity<?> saveProduct(Product product) {
        return ResponseEntity.ok(productRepository.save(product));
    }

    public ResponseEntity<?> deleteProductById(Long id) {
        if (!productRepository.existsById(id)) {
            return new ResponseEntity<>(new CatalogError("Отсутствует продукт с id " + id), HttpStatus.BAD_REQUEST);
        }
        productRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> changeDescriptionById(String description, Long id) {
        if (!productRepository.existsById(id)) {
            return new ResponseEntity<>(new CatalogError("Отсутствует продукт с id " + id), HttpStatus.BAD_REQUEST);
        }
        productRepository.updateDescriptionById(description, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

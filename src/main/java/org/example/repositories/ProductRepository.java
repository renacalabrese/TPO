package org.example.repositories;

import org.example.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, Integer> {
    Product findByProductId(Integer productId);
}

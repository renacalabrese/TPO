package org.example.repositories;

import org.example.model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CartRepository extends MongoRepository<Cart, String> {

    @SuppressWarnings("unchecked")
    Cart save(Cart cart);
    Optional<Cart> findById(Integer id);

}
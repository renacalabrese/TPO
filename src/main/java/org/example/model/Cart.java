package org.example.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;


@Document(collection = "carts")

@Data
public class Cart {
    @Id
    private Integer id;
    private Integer productId;
    private List<CartItem> items;


    public void addItem(CartItem cartItem){
        items.add(cartItem);
    }

}
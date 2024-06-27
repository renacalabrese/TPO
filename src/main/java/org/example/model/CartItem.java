package org.example.model;

import lombok.Data;

@Data
public class CartItem {
    private Product product;
    private int quantity;
    private float price;
}
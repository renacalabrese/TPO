package org.example.model;

import org.springframework.data.annotation.Id;

public class Order {
    @Id
    private int id;
    private String name;
    private float price;
}

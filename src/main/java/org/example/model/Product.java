package org.example.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Product {
    @Id
    private Integer ProductId;
    private String name;
    private float price;
    private String description;

}

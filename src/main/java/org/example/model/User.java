package org.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;


@Document(collection = "users")

@Data
public class User {
    @Id
    private Integer id;
    private String name;
    private String surname;
    private String address;
    private Integer phone;
    private String userName;
    private String password;
    private int dni;
    private Role role;
}

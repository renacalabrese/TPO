package org.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import java.time.LocalDate;

@Document(collection = "users")
@Data
public class User {
    @Id
    private Integer Id;
    private String userName;
    private String password;
    private String name;
    private String surname;
    private String address;
    private String dni;
    private LocalDate dayConnection;
    private long dailyConnectionTime;
    private Category category;
    private String role;
}

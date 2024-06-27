package org.example.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import java.util.List;

@Data
public class Order {
    @Id
    private int OrderId;
    private User user;
    private List<String> items;
    private boolean pagado;
    
}

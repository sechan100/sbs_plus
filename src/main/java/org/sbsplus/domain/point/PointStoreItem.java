package org.sbsplus.domain.point;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.sbsplus.util.Datetime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class PointStoreItem extends Datetime {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String name;
    
    private int price;
    
    private int quantity;
    
    private String description;
    
}

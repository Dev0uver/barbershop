package com.practice.barbershop.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Amenities {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private Integer price;
    private String name;
    @ManyToMany(mappedBy = "amenitiesList")
    private List<Order> orderList;
}

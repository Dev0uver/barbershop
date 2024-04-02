package com.practice.barbershop.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Map;

@Entity
@Table(name = "barbershop")
@Data
public class BarbershopEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String address;
    private String contactPhone;
    private String contactEmail;
    private Double averageRating;
    private Integer averageServiceCost;

    @ElementCollection
    @CollectionTable(name = "barbershop_schedule", joinColumns = @JoinColumn(name = "barbershop_id"))
    @MapKeyColumn(name = "day_of_week")
    @Column(name = "work_hours")
    private Map<String, String> schedule;

}

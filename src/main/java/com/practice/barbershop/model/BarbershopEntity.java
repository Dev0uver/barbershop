package com.practice.barbershop.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

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

    @OneToMany(mappedBy = "barbershopEntity",
    fetch = FetchType.EAGER)
    private List<Schedule> schedule;

}

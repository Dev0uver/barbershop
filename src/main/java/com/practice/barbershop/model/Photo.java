package com.practice.barbershop.model;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "barbers_photo")
@Data
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "barber_id")
    private Barber barber;

    @Override
    public String toString() {
        return this.id + "_" + this.name;
    }
}

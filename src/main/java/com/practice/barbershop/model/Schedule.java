package com.practice.barbershop.model;

import com.practice.barbershop.general.MyDayOfWeek;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "barbershop_schedule")
@Data
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private MyDayOfWeek dayOfWeek;
    private String workHours;
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "barbershop_id")
    private BarbershopEntity barbershopEntity;
}

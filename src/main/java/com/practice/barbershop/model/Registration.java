package com.practice.barbershop.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
/** ORM-model of Registration
 * @author David
 */
@Entity
@Table(name = "registrations")
@Data
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalTime time;
    private LocalDate day;
    //If user is unanimous get from form on site or get from Client table
    private String clientName;
    private String phone;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @ManyToOne
    @JoinColumn(name = "barber_id")
    private Barber barber;
    private LocalDateTime registrationTime;
    private Boolean canceled;
}

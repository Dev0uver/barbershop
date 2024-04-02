package com.practice.barbershop.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
//create when client came to barbershop
public class Order {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    //Get after client's cut hair is over
    private Integer price;
    //Get from registration
    private LocalTime time;
    private LocalDate day;
    //If user is unanimous get from form on site or get from Client table
    private String clientName;
    private String phone;
    //Evaluation of the order by the client 0 to 5 star
    private Byte mark;
    @ManyToOne
    @JoinColumn(name = "barber_id")
    private Barber barber;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "ordered_amenities",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "amenities_id")
    )
    private List<Amenities> amenitiesList;
}

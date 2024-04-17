package com.practice.barbershop.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

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
    @Column(name = "end_work")
    private LocalTime end;
    private LocalDate day;
    //If user is unanimous get from form on site or get from Client table
    private String clientName;
    private String phone;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "barbershop_id")
    private Barbershop barbershop;

    @ManyToOne
    @JoinColumn(name = "barber_id")
    private Barber barber;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "registration_amenity",
            joinColumns = @JoinColumn(name = "registration_id"),
            inverseJoinColumns = @JoinColumn(name = "amenities_id")
    )
    private List<Amenities> amenitiesList;

    @Column(name = "create_time")
    private LocalDateTime createTime;
    @Column(name = "last_update_time")
    private LocalDateTime lastUpdateTime;
    private Boolean canceled;

}

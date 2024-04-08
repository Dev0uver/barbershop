package com.practice.barbershop.model;

import com.practice.barbershop.general.BarberStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
/** ORM-model of Barber
 * @author David
 */
@Entity
@Data
public class Barber {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String phone;
    private String email;
    private BarberStatus barberStatus;

    @OneToMany(mappedBy = "barber")
    private List<Order> orderList;
    @OneToMany(mappedBy = "barber")
    private List<Registration> registrationList;
    @ManyToMany()
    @JoinTable(
            name = "barber_amenities",
            joinColumns = @JoinColumn(name = "barber_id"),
            inverseJoinColumns = @JoinColumn(name = "amenities_id")
    )
    private List<Amenities> amenitiesList;
}

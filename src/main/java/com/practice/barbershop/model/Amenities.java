package com.practice.barbershop.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalTime;
import java.util.List;
/** ORM-model of Amenity
 * @author David
 */
@Data
@Entity
public class Amenities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Integer price;
    private LocalTime duration;

    @ManyToMany(mappedBy = "amenitiesList")
    private List<Order> orderList;
    @ManyToMany(mappedBy = "amenitiesList")
    private List<Barber> barberList;
    @ManyToMany(mappedBy = "amenitiesList")
    private List<Registration> registrationList;
}

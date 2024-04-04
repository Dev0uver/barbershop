package com.practice.barbershop.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
/** ORM-model of Barbershop
 * @author David
 */
@Entity
@Table(name = "barbershop")
@Data
public class Barbershop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String address;
    private String contactPhone;
    private String contactEmail;
    private Double averageRating;
    private Integer averageServiceCost;

    @OneToMany(mappedBy = "barbershop",
    fetch = FetchType.EAGER)
    private List<Schedule> schedule;

}

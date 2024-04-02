package com.practice.barbershop.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Client {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String login;
    private String name;
    private String phone;
    private String password;
    private Boolean deleted;

    @OneToMany(mappedBy = "client")
    private List<Registration> registrationList;
    @OneToMany(mappedBy = "client")
    private List<Order> orderList;

}

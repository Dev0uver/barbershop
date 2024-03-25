package com.practice.barbershop.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class BarbershopDto implements Serializable {

    private Long id;
    private String address;
    private String contactPhone;
    private String contactEmail;
    private double averageRating;
    private int averageServiceCost;

    private Map<String, String> schedule;
}

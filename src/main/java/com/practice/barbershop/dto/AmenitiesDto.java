package com.practice.barbershop.dto;

import lombok.Data;


/**
 * Amenities dto for Amenities entity
 * @author David
 */
@Data
public class AmenitiesDto {
    private Long id;
    private Integer price;
    private String name;
}

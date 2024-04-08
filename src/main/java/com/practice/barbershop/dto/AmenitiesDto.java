package com.practice.barbershop.dto;

import lombok.Data;
import java.time.LocalTime;
import java.util.List;


/**
 * Amenities dto for Amenities entity
 * @author David
 */
@Data
public class AmenitiesDto {
    private Long id;
    private String name;
    private String description;
    private Integer price;
    private LocalTime duration;
    private List<BarberDto> barberDtoList;
}

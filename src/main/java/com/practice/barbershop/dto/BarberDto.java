package com.practice.barbershop.dto;

import com.practice.barbershop.general.BarberStatus;
import lombok.Data;

/**
 * Barber dto for Barber entity
 */
@Data
public class BarberDto {
    private Long id;
    private String phone;
    private String email;
    private BarberStatus barberStatus;
}

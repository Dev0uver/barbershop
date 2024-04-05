package com.practice.barbershop.dto;

import com.practice.barbershop.general.BarberDegree;
import com.practice.barbershop.general.BarberStatus;
import lombok.Data;

import java.util.List;

/**
 * Barber dto for Barber entity
 * @author David
 */
@Data
public class BarberDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String patronymic; //Отчество
    private String phone;
    private String email;
    private BarberStatus barberStatus;
    private BarberDegree barberDegree;
    private List<AmenitiesDto> amenitiesDtoList;
    private List<PhotoDto> photoDtoList;
}

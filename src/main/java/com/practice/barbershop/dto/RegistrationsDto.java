package com.practice.barbershop.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Registrations dto for Registrations entity
 * @author David
 */
@Data
public class RegistrationsDto {
    private Long id;
    private LocalTime time;
    private LocalTime end;
    private LocalDate day;
    private String clientName;
    private String phone;
    private LocalDateTime createTime;
    private LocalDateTime lastUpdateTime;
    private Boolean canceled;
    private ClientDto client;
    private BarberDto barber;
    private Long barbershopId;
    private List<AmenitiesDto> amenitiesDto;
}

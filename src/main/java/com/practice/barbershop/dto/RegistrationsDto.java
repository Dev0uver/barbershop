package com.practice.barbershop.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Registrations dto for Registrations entity
 * @author David
 */
@Data
public class RegistrationsDto {
    private Long id;
    private LocalTime time;
    private LocalDate day;
    private String clientName;
    private String phone;
    private LocalDateTime registrationTime;
    private Boolean canceled;
    private Long client_id;
    private Long barber_id;
}

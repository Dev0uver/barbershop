package com.practice.barbershop.service;

import com.practice.barbershop.dto.RegistrationsDto;
import com.practice.barbershop.general.MyService;
import com.practice.barbershop.model.Registration;

import java.time.LocalDate;
import java.time.LocalTime;

public interface RegistrationService extends MyService<RegistrationsDto, Registration> {

    RegistrationsDto canceled(LocalTime time, LocalDate date, Long barberId);

    RegistrationsDto setClient(RegistrationsDto registrationsDto);

    RegistrationsDto getByTimeAndDayAndBarberId(LocalTime time, LocalDate day, Long barberId, Long barbershopId);
}

package com.practice.barbershop.dto;

import com.practice.barbershop.general.MyDayOfWeek;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ScheduleDto {

    private Long id;
    private MyDayOfWeek dayOfWeek;
    private String workHours;
    private LocalDate date;
    private Long barbershopId;
}

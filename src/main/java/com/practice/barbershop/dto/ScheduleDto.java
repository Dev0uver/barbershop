package com.practice.barbershop.dto;

import com.practice.barbershop.general.MyDayOfWeek;
import lombok.Data;

import java.time.LocalDate;

/**
 * ScheduleDto dto for Schedule entity
 * @author David
 */
@Data
public class ScheduleDto {

    private Long id;
    private MyDayOfWeek dayOfWeek;
    private String workHours;
    private LocalDate date;
    private Long barbershopId;
}

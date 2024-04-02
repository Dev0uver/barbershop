package com.practice.barbershop.mapper;

import com.practice.barbershop.dto.ScheduleDto;
import com.practice.barbershop.model.BarbershopEntity;
import com.practice.barbershop.model.Schedule;

public class ScheduleMapper {

    public static ScheduleDto toDto(Schedule schedule) {
        ScheduleDto scheduleDto = new ScheduleDto();

        scheduleDto.setId(schedule.getId());
        scheduleDto.setDayOfWeek(schedule.getDayOfWeek());
        scheduleDto.setWorkHours(schedule.getWorkHours());
        scheduleDto.setDate(schedule.getDate());
        scheduleDto.setBarbershopId(schedule.getBarbershopEntity().getId());

        return scheduleDto;
    }

    public static Schedule toEntity(ScheduleDto scheduleDto) {
        Schedule schedule = new Schedule();

        schedule.setId(scheduleDto.getId());
        schedule.setDayOfWeek(scheduleDto.getDayOfWeek());
        schedule.setWorkHours(scheduleDto.getWorkHours());
        schedule.setDate(scheduleDto.getDate());

        BarbershopEntity barbershopEntity = new BarbershopEntity();
        barbershopEntity.setId(scheduleDto.getBarbershopId());
        schedule.setBarbershopEntity(barbershopEntity);

        return schedule;
    }
}

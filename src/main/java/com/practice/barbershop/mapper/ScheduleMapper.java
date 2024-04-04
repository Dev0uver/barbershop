package com.practice.barbershop.mapper;

import com.practice.barbershop.dto.ScheduleDto;
import com.practice.barbershop.model.Barbershop;
import com.practice.barbershop.model.Schedule;
/** Convert ScheduleDto to Schedule and Schedule to ScheduleDto
 * @author David
 */
public class ScheduleMapper {
    /**
     * Convert Schedule to ScheduleDto
     * @param entity Schedule
     * @return ScheduleDto
     */
    public static ScheduleDto toDto(Schedule entity) {
        ScheduleDto scheduleDto = new ScheduleDto();

        scheduleDto.setId(entity.getId());
        scheduleDto.setDayOfWeek(entity.getDayOfWeek());
        scheduleDto.setWorkHours(entity.getWorkHours());
        scheduleDto.setDate(entity.getDate());
        scheduleDto.setBarbershopId(entity.getBarbershop().getId());

        return scheduleDto;
    }
    /**
     * Convert ScheduleDto to Schedule
     * @param dto ScheduleDto
     * @return Schedule
     */
    public static Schedule toEntity(ScheduleDto dto) {
        Schedule schedule = new Schedule();

        schedule.setId(dto.getId());
        schedule.setDayOfWeek(dto.getDayOfWeek());
        schedule.setWorkHours(dto.getWorkHours());
        schedule.setDate(dto.getDate());

        Barbershop barbershop = new Barbershop();
        barbershop.setId(dto.getBarbershopId());
        schedule.setBarbershop(barbershop);

        return schedule;
    }
}

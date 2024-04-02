package com.practice.barbershop.service;

import com.practice.barbershop.dto.ScheduleDto;
import com.practice.barbershop.mapper.ScheduleMapper;
import com.practice.barbershop.model.Schedule;
import com.practice.barbershop.repository.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Transactional
    public void update(ScheduleDto scheduleDto) {
        Schedule schedule = scheduleRepository.findById(scheduleDto.getId()).orElseThrow(() -> new RuntimeException("Not found"));
        schedule.setDayOfWeek(scheduleDto.getDayOfWeek());
        schedule.setWorkHours(scheduleDto.getWorkHours());
        schedule.setDate(scheduleDto.getDate());

        scheduleRepository.save(schedule);
    }

    @Transactional
    public void delete(ScheduleDto scheduleDto) {
        Schedule schedule = ScheduleMapper.toEntity(scheduleDto);
        scheduleRepository.delete(schedule);
    }
}

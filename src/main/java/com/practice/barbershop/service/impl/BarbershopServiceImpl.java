package com.practice.barbershop.service.impl;

import com.practice.barbershop.dto.BarbershopDto;
import com.practice.barbershop.mapper.BarbershopMapper;
import com.practice.barbershop.mapper.ScheduleMapper;
import com.practice.barbershop.model.Barbershop;
import com.practice.barbershop.model.Schedule;
import com.practice.barbershop.repository.BarbershopRepository;
import com.practice.barbershop.repository.ScheduleRepository;
import com.practice.barbershop.service.ScheduleService;
import com.practice.barbershop.service.pattern.BarbershopService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BarbershopServiceImpl implements BarbershopService {

    private final BarbershopRepository barbershopRepository;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleService scheduleService;

    @Override
    @Transactional
    public void createBarbershop(BarbershopDto barbershopDto) {
        Barbershop barbershopEntity = BarbershopMapper.toEntity(barbershopDto);
        List<Schedule> scheduleList = barbershopEntity.getSchedule();
        Barbershop barbershop = barbershopRepository.save(barbershopEntity);
        barbershop = barbershopRepository.findById(barbershop.getId())
                .orElseThrow(() -> new RuntimeException("Not found"));
        for (Schedule schedule : scheduleList) {
            schedule.setBarbershop(barbershop);
            scheduleRepository.save(schedule);
        }
    }

    @Override
    public BarbershopDto getBarbershop(Long id) {
        Barbershop barbershop = barbershopRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("barbershop with id=" + id + " not found"));
        return BarbershopMapper.toDto(barbershop);
    }

    @Override
    @Transactional
    public boolean updateBarbershop(Long id, BarbershopDto barbershopDto) {
        Optional<Barbershop> optionalBarbershopEntity = barbershopRepository.findById(id);
        if (optionalBarbershopEntity.isPresent()) {
            Barbershop barbershop = optionalBarbershopEntity.get();
            barbershop.setAddress(barbershopDto.getAddress());
            barbershop.setContactPhone(barbershopDto.getContactPhone());
            barbershop.setContactEmail(barbershopDto.getContactEmail());
            barbershop.setAverageRating(barbershopDto.getAverageRating());
            barbershop.setAverageServiceCost(barbershopDto.getAverageServiceCost());
            barbershop.setSchedule(barbershopDto.getSchedule().stream()
                    .map(ScheduleMapper::toEntity)
                    .collect(Collectors.toList()));

            for (Schedule newSchedule : barbershop.getSchedule()) {
                Schedule schedule = scheduleRepository
                        .findByDayOfWeekAndBarbershopId(newSchedule.getDayOfWeek(), barbershop.getId())
                        .orElseThrow(() -> new RuntimeException("Not found"));
                if (newSchedule.getWorkHours() != null) {
                    schedule.setWorkHours(newSchedule.getWorkHours());
                }
                if (newSchedule.getDate() != null) {
                    schedule.setDate(newSchedule.getDate());
                }
                scheduleService.update(ScheduleMapper.toDto(schedule));
            }
            barbershopRepository.save(barbershop);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean deleteBarbershop(Long id) {
        Barbershop barbershop = barbershopRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
            for (Schedule schedule: barbershop.getSchedule()) {
                scheduleService.delete(ScheduleMapper.toDto(schedule));
            }
            barbershopRepository.deleteById(id);
            return true;
    }

    @Override
    @Scheduled(cron = "${interval-in-cron}")
    @Transactional
    public void updateSchedule() {
        System.out.println("check");
        List<Barbershop> barbershopList = getAllBarberShops();
        for (Barbershop barbershop : barbershopList) {
            for (Schedule schedule : barbershop.getSchedule()) {
                schedule.setDate(LocalDate.now().plusDays(schedule.getDayOfWeek().ordinal()));
                scheduleRepository.save(schedule);
            }
        }
    }

    @Override
    public List<Barbershop> getAllBarberShops() {
        return barbershopRepository.findAll();
    }

}

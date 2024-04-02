package com.practice.barbershop.service.impl;

import com.practice.barbershop.dto.BarbershopDto;
import com.practice.barbershop.mapper.BarbershopMapper;
import com.practice.barbershop.mapper.ScheduleMapper;
import com.practice.barbershop.model.BarbershopEntity;
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
        BarbershopEntity barbershopEntity = BarbershopMapper.toEntity(barbershopDto);
        List<Schedule> scheduleList = barbershopEntity.getSchedule();
        BarbershopEntity barbershop = barbershopRepository.save(barbershopEntity);
        barbershop = barbershopRepository.findById(barbershop.getId())
                .orElseThrow(() -> new RuntimeException("Not found"));
        for (Schedule schedule : scheduleList) {
            schedule.setBarbershopEntity(barbershop);
            scheduleRepository.save(schedule);
        }
    }

    @Override
    public BarbershopDto getBarbershop(Long id) {
        BarbershopEntity barbershopEntity = barbershopRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("barbershop with id=" + id + " not found"));
//        List<Schedule> scheduleList = scheduleRepository
//                .findAllByBarbershopEntityId(barbershopEntity.getId());
//        barbershopEntity.setSchedule(scheduleList);
        return BarbershopMapper.toDto(barbershopEntity);
    }

    @Transactional
    @Override
    public boolean updateBarbershop(Long id, BarbershopDto barbershopDto) {
        Optional<BarbershopEntity> optionalBarbershopEntity = barbershopRepository.findById(id);
        if (optionalBarbershopEntity.isPresent()) {
            BarbershopEntity barbershopEntity = optionalBarbershopEntity.get();
            barbershopEntity.setAddress(barbershopDto.getAddress());
            barbershopEntity.setContactPhone(barbershopDto.getContactPhone());
            barbershopEntity.setContactEmail(barbershopDto.getContactEmail());
            barbershopEntity.setAverageRating(barbershopDto.getAverageRating());
            barbershopEntity.setAverageServiceCost(barbershopDto.getAverageServiceCost());
            barbershopEntity.setSchedule(barbershopDto.getSchedule().stream()
                    .map(ScheduleMapper::toEntity)
                    .collect(Collectors.toList()));

            for (Schedule newSchedule : barbershopEntity.getSchedule()) {
                Schedule schedule = scheduleRepository
                        .findByDayOfWeekAndBarbershopEntityId(newSchedule.getDayOfWeek(), barbershopEntity.getId())
                        .orElseThrow(() -> new RuntimeException("Not found"));
                if (newSchedule.getWorkHours() != null) {
                    schedule.setWorkHours(newSchedule.getWorkHours());
                }
                if (newSchedule.getDate() != null) {
                    schedule.setDate(newSchedule.getDate());
                }
                scheduleService.update(ScheduleMapper.toDto(schedule));
            }
            barbershopRepository.save(barbershopEntity);
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public boolean deleteBarbershop(Long id) {
        BarbershopEntity barbershopEntity = barbershopRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
            for (Schedule schedule: barbershopEntity.getSchedule()) {
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
        List<BarbershopEntity> barbershopEntityList = getAllBarberShops();
        for (BarbershopEntity barbershopEntity : barbershopEntityList) {
            for (Schedule schedule : barbershopEntity.getSchedule()) {
                schedule.setDate(LocalDate.now().plusDays(schedule.getDayOfWeek().ordinal()));
                scheduleRepository.save(schedule);
            }
        }
    }

    @Override
    public List<BarbershopEntity> getAllBarberShops() {
        return barbershopRepository.findAll();
    }


}

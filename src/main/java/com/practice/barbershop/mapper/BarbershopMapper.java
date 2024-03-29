package com.practice.barbershop.mapper;

import com.practice.barbershop.dto.BarbershopDto;
import com.practice.barbershop.model.BarbershopEntity;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BarbershopMapper {

    public static BarbershopEntity toEntity(BarbershopDto barbershopDto) {
        BarbershopEntity barbershopEntity = new BarbershopEntity();
        barbershopEntity.setId(barbershopDto.getId());
        barbershopEntity.setAddress(barbershopDto.getAddress());
        barbershopEntity.setContactPhone(barbershopDto.getContactPhone());
        barbershopEntity.setContactEmail(barbershopDto.getContactEmail());
        barbershopEntity.setAverageRating(barbershopDto.getAverageRating());
        barbershopEntity.setAverageServiceCost(barbershopDto.getAverageServiceCost());
        barbershopEntity.setSchedule(barbershopDto.getSchedule());
        return barbershopEntity;
    }

    public static Map<String, String> orderSchedule(Map<String, String> schedule) {
        List<String> week = List.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
        Map<String, String> sorted = new LinkedHashMap<>();
        for (String day: week) {
            sorted.put(day, schedule.get(day));
        }
        return sorted;
    }
}

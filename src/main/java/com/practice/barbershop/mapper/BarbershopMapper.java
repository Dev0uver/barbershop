package com.practice.barbershop.mapper;

import com.practice.barbershop.dto.BarbershopDto;
import com.practice.barbershop.model.BarbershopEntity;

import java.util.stream.Collectors;

public class BarbershopMapper {

    public static BarbershopEntity toEntity(BarbershopDto barbershopDto) {
        BarbershopEntity barbershopEntity = new BarbershopEntity();
        barbershopEntity.setId(barbershopDto.getId());
        barbershopEntity.setAddress(barbershopDto.getAddress());
        barbershopEntity.setContactPhone(barbershopDto.getContactPhone());
        barbershopEntity.setContactEmail(barbershopDto.getContactEmail());
        barbershopEntity.setAverageRating(barbershopDto.getAverageRating());
        barbershopEntity.setAverageServiceCost(barbershopDto.getAverageServiceCost());
        barbershopEntity.setSchedule(barbershopDto.getSchedule().stream()
                .map(ScheduleMapper::toEntity)
                .collect(Collectors.toList()));
        return barbershopEntity;
    }

    public static BarbershopDto toDto(BarbershopEntity barbershopEntity) {
        BarbershopDto barbershopDto = new BarbershopDto();
        barbershopDto.setId(barbershopEntity.getId());
        barbershopDto.setAddress(barbershopEntity.getAddress());
        barbershopDto.setContactPhone(barbershopEntity.getContactPhone());
        barbershopDto.setContactEmail(barbershopEntity.getContactEmail());
        barbershopDto.setAverageRating(barbershopEntity.getAverageRating());
        barbershopDto.setAverageServiceCost(barbershopEntity.getAverageServiceCost());
        barbershopDto.setSchedule(barbershopEntity.getSchedule().stream()
                .map(ScheduleMapper::toDto)
                .collect(Collectors.toList()));
        return barbershopDto;
    }
}

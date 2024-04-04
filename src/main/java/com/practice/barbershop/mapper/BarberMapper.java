package com.practice.barbershop.mapper;


import com.practice.barbershop.dto.BarberDto;
import com.practice.barbershop.model.Barber;

import java.util.stream.Collectors;

/** Convert Barber to BarberDto and BarberDto to Barber
 * @author David
 */
public class BarberMapper {
    /**
     * Convert Barber to BarberDto
     * @param entity Barber
     * @return BarberDto
     */
    public static BarberDto toDto(Barber entity) {
        BarberDto barber = new BarberDto();

        barber.setId(entity.getId());
        barber.setEmail(entity.getEmail());
        barber.setBarberStatus(entity.getBarberStatus());
        barber.setPhone(entity.getPhone());

        barber.setAmenitiesDtoList(entity.getAmenitiesList()
                .stream().map(AmenitiesMapper::toDto)
                .collect(Collectors.toList()));

        return barber;
    }
    /**
     * Convert BarberDto to Barber
     * @param dto BarberDto
     * @return Barber
     */
    public static Barber toEntity(BarberDto dto) {
        Barber barber = new Barber();

        barber.setId(dto.getId());
        barber.setPhone(dto.getPhone());
        barber.setEmail(dto.getEmail());
        barber.setBarberStatus(dto.getBarberStatus());

        barber.setAmenitiesList(dto.getAmenitiesDtoList()
                .stream().map(AmenitiesMapper::toEntity)
                .collect(Collectors.toList()));

        return barber;
    }
}

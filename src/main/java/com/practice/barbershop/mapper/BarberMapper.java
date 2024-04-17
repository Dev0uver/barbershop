package com.practice.barbershop.mapper;


import com.practice.barbershop.dto.BarberDto;
import com.practice.barbershop.model.Barber;

import java.util.ArrayList;
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
        barber.setFirstName(entity.getFirstName());
        barber.setLastName(entity.getLastName());
        barber.setPatronymic(entity.getPatronymic());
        barber.setEmail(entity.getEmail());
        barber.setPhone(entity.getPhone());
        barber.setBarberStatus(entity.getBarberStatus());
        barber.setBarberDegree(entity.getBarberDegree());

        if (entity.getAmenitiesList() != null) {
            barber.setAmenitiesDtoList(entity.getAmenitiesList()
                    .stream().map(AmenitiesMapper::toDto)
                    .collect(Collectors.toList()));
        } else {
            barber.setAmenitiesDtoList(new ArrayList<>());
        }

        if (entity.getAmenitiesList() != null) {
            barber.setPhotoDtoList(entity.getPhotoList()
                    .stream().map(PhotoMapper::toDto)
                    .collect(Collectors.toList()));
        } else {
            barber.setPhotoDtoList(new ArrayList<>());
        }

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
        barber.setFirstName(dto.getFirstName());
        barber.setLastName(dto.getLastName());
        barber.setPatronymic(dto.getPatronymic());
        barber.setPhone(dto.getPhone());
        barber.setEmail(dto.getEmail());
        barber.setBarberStatus(dto.getBarberStatus());
        barber.setBarberDegree(dto.getBarberDegree());

        if (dto.getAmenitiesDtoList() != null) {
            barber.setAmenitiesList(dto.getAmenitiesDtoList()
                    .stream().map(AmenitiesMapper::toEntity)
                    .collect(Collectors.toList()));
        } else {
            barber.setAmenitiesList(new ArrayList<>());
        }

        if (dto.getPhotoDtoList() != null) {
            barber.setPhotoList(dto.getPhotoDtoList()
                    .stream().map(PhotoMapper::toEntity)
                    .collect(Collectors.toList()));
        } else {
            barber.setPhotoList(new ArrayList<>());
        }

        return barber;
    }
}

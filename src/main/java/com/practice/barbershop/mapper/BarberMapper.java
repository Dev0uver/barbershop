package com.practice.barbershop.mapper;


import com.practice.barbershop.dto.BarberDto;
import com.practice.barbershop.model.Barber;


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

        return barber;
    }
}

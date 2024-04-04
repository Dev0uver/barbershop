package com.practice.barbershop.mapper;

import com.practice.barbershop.dto.BarbershopDto;
import com.practice.barbershop.model.Barbershop;

import java.util.stream.Collectors;

/** Convert BarbershopDto to Barbershop and Barbershop to BarbershopDto
 * @author David
 */
public class BarbershopMapper {

    /**
     * Convert BarbershopDto to Barbershop
     * @param dto BarbershopDto
     * @return Barbershop
     */
    public static Barbershop toEntity(BarbershopDto dto) {
        Barbershop barbershop = new Barbershop();
        barbershop.setId(dto.getId());
        barbershop.setAddress(dto.getAddress());
        barbershop.setContactPhone(dto.getContactPhone());
        barbershop.setContactEmail(dto.getContactEmail());
        barbershop.setAverageRating(dto.getAverageRating());
        barbershop.setAverageServiceCost(dto.getAverageServiceCost());

        barbershop.setSchedule(dto.getSchedule().stream()
                .map(ScheduleMapper::toEntity)
                .collect(Collectors.toList()));
        return barbershop;
    }
    /**
     * Convert Barbershop to BarbershopDto
     * @param entity Barbershop
     * @return BarbershopDto
     */
    public static BarbershopDto toDto(Barbershop entity) {
        BarbershopDto barbershopDto = new BarbershopDto();
        barbershopDto.setId(entity.getId());
        barbershopDto.setAddress(entity.getAddress());
        barbershopDto.setContactPhone(entity.getContactPhone());
        barbershopDto.setContactEmail(entity.getContactEmail());
        barbershopDto.setAverageRating(entity.getAverageRating());
        barbershopDto.setAverageServiceCost(entity.getAverageServiceCost());

        barbershopDto.setSchedule(entity.getSchedule().stream()
                .map(ScheduleMapper::toDto)
                .collect(Collectors.toList()));
        return barbershopDto;
    }
}

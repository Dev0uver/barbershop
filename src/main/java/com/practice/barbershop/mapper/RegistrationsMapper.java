package com.practice.barbershop.mapper;

import com.practice.barbershop.dto.RegistrationsDto;
import com.practice.barbershop.model.Barber;
import com.practice.barbershop.model.Barbershop;
import com.practice.barbershop.model.Client;
import com.practice.barbershop.model.Registration;

import java.util.ArrayList;
import java.util.stream.Collectors;

/** Convert RegistrationsDto to Registration and Registration to RegistrationsDto
 * @author David
 */
public class RegistrationsMapper {
    /**
     * Convert Registration to RegistrationsDto
     * @param entity Registration
     * @return RegistrationsDto
     */
    public static RegistrationsDto toDto(Registration entity) {
        RegistrationsDto registrations = new RegistrationsDto();

        registrations.setId(entity.getId());
        registrations.setCreateTime(entity.getCreateTime());
        registrations.setLastUpdateTime(entity.getLastUpdateTime());
        registrations.setDay(entity.getDay());
        registrations.setTime(entity.getTime());
        registrations.setCanceled(entity.getCanceled());
        registrations.setPhone(entity.getPhone());
        registrations.setClientName(entity.getClientName());
        registrations.setBarbershopId(entity.getBarbershop().getId());
        registrations.setEnd(entity.getEnd());

        registrations.setClient(ClientMapper.toDto(entity.getClient()));
        registrations.setBarber(BarberMapper.toDto(entity.getBarber()));

        if (entity.getAmenitiesList() != null) {
            registrations.setAmenitiesDto(entity.getAmenitiesList()
                    .stream()
                    .map(AmenitiesMapper::toDto)
                    .collect(Collectors.toList()));
        } else {
            registrations.setAmenitiesDto(new ArrayList<>());
        }

        return registrations;
    }
    /**
     * Convert RegistrationsDto to Registration
     * @param dto RegistrationsDto
     * @return Registration
     */
    public static Registration toEntity(RegistrationsDto dto) {
        Registration registration = new Registration();

        registration.setId(dto.getId());
        registration.setCreateTime(dto.getCreateTime());
        registration.setLastUpdateTime(dto.getLastUpdateTime());
        registration.setDay(dto.getDay());
        registration.setTime(dto.getTime());
        registration.setCanceled(dto.getCanceled());
        registration.setClientName(dto.getClientName());
        registration.setPhone(dto.getPhone());
        registration.setEnd(dto.getEnd());

        Client client = new Client();
        client.setId(dto.getClient().getId());
        registration.setClient(client);

        Barber barber = new Barber();
        barber.setId(dto.getBarber().getId());
        registration.setBarber(barber);

        Barbershop barbershop = new Barbershop();
        barbershop.setId(dto.getBarbershopId());
        registration.setBarbershop(barbershop);

        if (dto.getAmenitiesDto() != null) {
            registration.setAmenitiesList(dto.getAmenitiesDto()
                    .stream().map(AmenitiesMapper::toEntity)
                    .collect(Collectors.toList()));
        } else {
            registration.setAmenitiesList(new ArrayList<>());
        }

        return registration;
    }
}

package com.practice.barbershop.mapper;

import com.practice.barbershop.dto.RegistrationsDto;
import com.practice.barbershop.model.Barber;
import com.practice.barbershop.model.Client;
import com.practice.barbershop.model.Registration;

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
        registrations.setRegistrationTime(entity.getRegistrationTime());
        registrations.setDay(entity.getDay());
        registrations.setTime(entity.getTime());
        registrations.setCanceled(entity.getCanceled());
        registrations.setPhone(entity.getPhone());
        registrations.setClientName(entity.getClientName());
        registrations.setClient_id(entity.getClient().getId());
        registrations.setBarber_id(entity.getBarber().getId());

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
        registration.setRegistrationTime(dto.getRegistrationTime());
        registration.setDay(dto.getDay());
        registration.setTime(dto.getTime());
        registration.setCanceled(dto.getCanceled());
        registration.setClientName(dto.getClientName());
        registration.setPhone(dto.getPhone());

        Client client = new Client();
        client.setId(dto.getClient_id());
        registration.setClient(client);

        Barber barber = new Barber();
        barber.setId(dto.getBarber_id());
        registration.setBarber(barber);

        return registration;
    }
}

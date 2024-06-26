package com.practice.barbershop.service;

import com.practice.barbershop.dto.RegistrationsDto;
import com.practice.barbershop.general.MyService;
import com.practice.barbershop.mapper.RegistrationsMapper;
import com.practice.barbershop.model.Client;
import com.practice.barbershop.model.Registration;
import com.practice.barbershop.repository.RegistrationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This is service class for maintain the registration entity
 */
@Service
@AllArgsConstructor
public class RegistrationService implements MyService<RegistrationsDto, Registration> {
    private final RegistrationRepository registrationRepository;
    private final ClientService clientService;
    private final BarberService barberService;
    /**
     * Return Registration entity by his id
     * @param id entity id
     * @return Registration entity
     */
    @Override
    public Registration getEntityById(Long id) {
        return registrationRepository.getRegistrationById(id)
                .orElseThrow(() -> new RuntimeException("Registration with id=" + id + " not found."));
    }

    /**
     * Return Registration dto by his id
     * @param id ID
     * @return Registration dto
     */
    @Override
    public RegistrationsDto getDtoById(Long id) {
        return RegistrationsMapper.toDto(registrationRepository.getRegistrationById(id)
                .orElseThrow(() -> new RuntimeException("Registration with id=" + id + " not found.")));
    }


    /**
     * Save entity in db
     * @param dto Registration dto object
     * @throws RuntimeException Registration has already been created
     */
    @Override
    @Transactional
    public RegistrationsDto save(RegistrationsDto dto) {
        dto.setId(null);
        Registration entity = RegistrationsMapper.toEntity(dto);

        List<Registration> registrationList = registrationRepository.
                getRegistrationsByTimeAndDayAndBarber(entity.getTime(),entity.getDay(), entity.getBarber());

        for (Registration registration: registrationList) {
            if (!registration.getCanceled()) {
                throw new RuntimeException("Registration on " + entity.getDay() +
                        " " + entity.getTime() + " has already been created.");
            }
        }

        Registration registration = registrationRepository.save(entity);
        return RegistrationsMapper.toDto(registration);
    }

    @Override
    @Transactional
    public RegistrationsDto update(RegistrationsDto dto) {
        return RegistrationsMapper.toDto(registrationRepository.save(RegistrationsMapper.toEntity(dto)));
    }

    @Override
    public List<RegistrationsDto> getAllDto() {
        List<Registration> registrations = registrationRepository.findAll();
        return registrations.stream().map(RegistrationsMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Cancel registration
     * @param time The time of registration
     * @param date The date of registration
     * @param barberId Barber
     * @throws RuntimeException Registration not found
     */
    @Transactional
    public RegistrationsDto canceled(LocalTime time, LocalDate date, Long barberId) throws RuntimeException {

        List<Registration> registrationList = registrationRepository.
                getRegistrationsByTimeAndDayAndBarber(time,date,barberService.getEntityById(barberId));
        if (registrationList != null) {
            for (Registration registration: registrationList) {
                if (!registration.getCanceled()) {
                    registration.setCanceled(Boolean.TRUE);
                    registrationRepository.save(registration);
                    return RegistrationsMapper.toDto(registration);
                }
            }
        }
        throw new RuntimeException("Registration on " + date +
                " " + time + " not found.");

    }

    /**
     * For link Registration to Client, Barber and set basic initialization
     * @return RegistrationDto obj link to the client and barber
     */
    public RegistrationsDto setClient(RegistrationsDto registrationsDto) {
        Client client = clientService.getEntityById(registrationsDto.getClient_id());

        registrationsDto.setRegistrationTime(LocalDateTime.now());
        registrationsDto.setPhone(client.getPhone());
        registrationsDto.setClientName(client.getName());
        registrationsDto.setCanceled(Boolean.FALSE);

        return registrationsDto;
    }
}

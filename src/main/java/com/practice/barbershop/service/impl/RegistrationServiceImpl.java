package com.practice.barbershop.service.impl;

import com.practice.barbershop.dto.*;
import com.practice.barbershop.general.MyDayOfWeek;
import com.practice.barbershop.mapper.RegistrationsMapper;
import com.practice.barbershop.model.Client;
import com.practice.barbershop.model.Registration;
import com.practice.barbershop.repository.RegistrationRepository;
import com.practice.barbershop.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This is service class for maintain the registration entity
 */
@Service
@AllArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final RegistrationRepository registrationRepository;
    private final ClientService clientService;
    private final BarberService barberService;
    private final ScheduleService scheduleService;
    private final AmenitiesService amenitiesService;
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
        return RegistrationsMapper.toDto(getEntityById(id));
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
        dto = setClient(dto);

        if (dto.getAmenitiesDto() == null) {
            throw new RuntimeException("Please select amenities.");
        }

        dto.setAmenitiesDto(dto.getAmenitiesDto().stream()
                .map((amenitiesDto -> amenitiesService.getDtoById(amenitiesDto.getId())))
                .collect(Collectors.toList()));

        BarberDto barberDto = barberService.getDtoById(dto.getBarber().getId());

        dto.setEnd(dto.getTime());
        for (AmenitiesDto amenity: dto.getAmenitiesDto()) {
            boolean canUse = false;
            for (AmenitiesDto barberAmenity: barberDto.getAmenitiesDtoList()) {
                if (Objects.equals(amenity.getId(), barberAmenity.getId())) {
                    canUse = true;

                    dto.setEnd(dto.getEnd().plusHours(amenity.getDuration().getHour()));
                    dto.setEnd(dto.getEnd().plusMinutes(amenity.getDuration().getMinute()));

                    break;
                }
            }
            if (!canUse) {
                throw new RuntimeException("This barber can't make amenity with id=" + amenity.getId());
            }
        }

        Registration entity = RegistrationsMapper.toEntity(dto);

        validationRegistrationCollision(entity);

        entity.setCreateTime(LocalDateTime.now());
        entity.setLastUpdateTime(LocalDateTime.now());

        Registration registration = registrationRepository.save(entity);

        RegistrationsDto registrationsDto = RegistrationsMapper.toDto(registration);

        //Check barber and client exists
        ClientDto clientDto = clientService.getDtoById(registration.getBarber().getId());

        registrationsDto.setBarber(barberDto);
        registrationsDto.setClient(clientDto);

        return registrationsDto;
    }

    @Override
    @Transactional
    public RegistrationsDto update(RegistrationsDto dto) {
        dto.setLastUpdateTime(LocalDateTime.now());
        Registration registration = registrationRepository.save(RegistrationsMapper.toEntity(dto));
        return RegistrationsMapper.toDto(registration);
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
    @Override
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
    @Override
    public RegistrationsDto setClient(RegistrationsDto registrationsDto) {
        Client client = clientService.getEntityById(registrationsDto.getClient().getId());

        registrationsDto.setPhone(client.getPhone());
        registrationsDto.setClientName(client.getName());
        registrationsDto.setCanceled(Boolean.FALSE);

        return registrationsDto;
    }

    @Override
    public RegistrationsDto getByTimeAndDayAndBarberId(LocalTime time, LocalDate day, Long barberId, Long barbershopId) {

        Registration registrations = registrationRepository
                .getRegistrationsByTimeAndDayAndBarberAndBarbershopIdAndCanceled(time, day, barberService.getEntityById(barberId), barbershopId, false)
                .orElseThrow(() -> new RuntimeException("Registration on " + day + " " + time + " to barber with id=" + barberId + " not found or canceled."));

        return RegistrationsMapper.toDto(registrations);
    }

    private void validationRegistrationCollision(Registration registration) {

        //Get day of week
        Calendar c = Calendar.getInstance();
        Date date = Date.from(registration.getDay().atStartOfDay(ZoneId.systemDefault()).toInstant());
        c.setTime(date); // your date is an object of type Date

        MyDayOfWeek dayOfWeek = MyDayOfWeek.valueOf(DayOfWeek.of(c.get(Calendar.DAY_OF_WEEK)).minus(1).name().toUpperCase()); // this will for example return 3 for tuesday
        //Get schedule for this day
        ScheduleDto schedule = scheduleService.getScheduleForCurrentDay(dayOfWeek, registration.getBarbershop().getId());

        //Check barbershop closed or not
        if (schedule.getWorkHours().equals("Closed")) {
            throw new RuntimeException("Sorry we are closed in " + registration.getDay());
        }

        List<LocalTime> times = Arrays.stream(schedule.getWorkHours().split(" - ")).map(LocalTime::parse).toList();

        //Check registration start time after barbershop open and registration end time before barbershop close


        if (registration.getTime().isBefore(times.get(0))) {
            throw new RuntimeException("The barbershop opens in " + times.get(0));
        }

        if (registration.getEnd().isBefore(registration.getTime())) {
            throw new RuntimeException("Registration start must be before registration end");
        }

        if (registration.getEnd().isAfter(times.get(1))) {
            throw new RuntimeException("The barbershop closed in " + times.get(1) + " your registration end in " + registration.getEnd());
        }

        //Get List of registrations for this time
        //TODO переделать запрос, сразу вытаскивать активную регистрацию, если есть
        List<Registration> registrationList = registrationRepository.
                getRegistrationsByTimeAndDayAndBarberAndBarbershopId(
                        registration.getTime(),registration.getDay(), registration.getBarber(), registration.getBarbershop().getId());

        //Check no active registration or else throw
        for (Registration reg: registrationList) {
            if (!reg.getCanceled()) {
                throw new RuntimeException("Registration on " + registration.getDay() +
                        " " + registration.getTime() + " has already been created.");
            }
        }

        //Get all active registrations for this day and barber
        List<Registration> registrationsForCurDay = registrationRepository
                .getRegistrationByDayAndBarberId(registration.getDay(), registration.getBarber().getId());


        if (registrationsForCurDay != null) {
            List<RegistrationsDto> registrationsDtoList = registrationsForCurDay
                    .stream().map(RegistrationsMapper::toDto).toList();

            boolean collision = false;
            for (RegistrationsDto registrationsDto: registrationsDtoList) {
                // Схема проверок коллизий для лучшего понимания
                // | - Начало и конец уже существующей записи
                // () - Начало и конец потенциальной записи
                // _|____|___(____)_____ (OK) Good
                // ___(___)____|___|__ (OK) Good

                 // ____|___(____)___|__  !Collision
                 if (registration.getTime().isAfter(registrationsDto.getTime()) && registration.getEnd().isBefore(registrationsDto.getEnd())) {
                     collision = true;
                 }
                // _____(___|____)___|__  !Collision
                if (registration.getTime().isBefore(registrationsDto.getTime()) && registration.getEnd().isAfter(registrationsDto.getTime())) {
                    collision = true;
                }
                // _____|___(____|___)__  !Collision
                if (registration.getTime().isBefore(registrationsDto.getEnd()) && registration.getEnd().isAfter(registrationsDto.getEnd())) {
                    collision = true;
                }
                // _____(___|____|___)__  !Collision
                if (registration.getTime().isBefore(registrationsDto.getTime()) && registration.getEnd().isAfter(registrationsDto.getEnd())) {
                    collision = true;
                }

                if (collision) {
                    throw new RuntimeException("Collision with registration on " + registrationsDto.getDay() + " to barber with id=" + registrationsDto.getBarber().getId()
                            + " this registration start in " + registrationsDto.getTime() + " and end in " + registrationsDto.getEnd()
                            + ".\nYour registration start in " + registration.getTime() + " and end in " + registration.getEnd());
                }
            }
        }
    }
}

package com.practice.barbershop.service;

import com.practice.barbershop.dto.AmenitiesDto;
import com.practice.barbershop.dto.BarberDto;
import com.practice.barbershop.general.MyService;
import com.practice.barbershop.mapper.AmenitiesMapper;
import com.practice.barbershop.mapper.BarberMapper;
import com.practice.barbershop.model.Amenities;
import com.practice.barbershop.model.Barber;
import com.practice.barbershop.repository.BarberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * This is service class for maintain the barber entity
 */
@Service
@AllArgsConstructor
public class BarberService implements MyService<BarberDto, Barber> {
    private final BarberRepository barberRepository;
    private final AmenitiesService amenitiesService;

    /**
     * Return Barber entity by his id
     * @param id entity id
     * @return Barber dto
     */
    @Override
    public BarberDto getDtoById(Long id) {
        return BarberMapper.toDto(barberRepository.getBarberById(id)
                .orElseThrow(() -> new RuntimeException("Barber with id=" + id + " not found.")));
    }

    /**
     * Return Barber entity by his id
     * @param id entity id
     * @return Barber entity
     */
    @Override
    public Barber getEntityById(Long id) {
        return barberRepository.getBarberById(id)
                .orElseThrow(() -> new RuntimeException("Barber with id=" + id + " not found."));
    }

    /**
     * Save or change entity in db
     * @param dto entity object
     */
    @Override
    @Transactional
    public BarberDto save(BarberDto dto) {
        dto.setId(null);
        Barber savedBarber = barberRepository.save(BarberMapper.toEntity(dto));
        return BarberMapper.toDto(savedBarber);
    }

    @Override
    public BarberDto update(BarberDto dto) {
        return BarberMapper.toDto(barberRepository.save(BarberMapper.toEntity(dto)));
    }

    @Override
    public List<BarberDto> getAllDto() {
        List<Barber> barbers = barberRepository.findAll();
        return barbers.stream().map(BarberMapper::toDto)
                .collect(Collectors.toList());
    }
    @Transactional
    public void addAmenityToBarber(Long barberId, Long amenityId) {
        Barber barber = getEntityById(barberId);
        AmenitiesDto amenities = amenitiesService.getDtoById(amenityId);

        boolean contains = false;
        for (Amenities amenity : barber.getAmenitiesList()) {
            if (Objects.equals(amenity.getId(), amenities.getId())) {
                contains = true;
                break;
            }
        }
        if (!contains) {
            barber.getAmenitiesList().add(AmenitiesMapper.toEntity(amenities));
            barberRepository.save(barber);
        } else {
            throw new RuntimeException("Amenity have already been added for this barber.");
        }
    }
}

package com.practice.barbershop.service;

import com.practice.barbershop.dto.AmenitiesDto;
import com.practice.barbershop.dto.BarberDto;
import com.practice.barbershop.general.MyService;
import com.practice.barbershop.mapper.AmenitiesMapper;
import com.practice.barbershop.mapper.BarberMapper;
import com.practice.barbershop.model.Amenities;
import com.practice.barbershop.model.Barber;
import com.practice.barbershop.repository.AmenitiesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AmenitiesService implements MyService<AmenitiesDto, Amenities> {
    private final AmenitiesRepository amenitiesRepository;
    @Override
    public AmenitiesDto getDtoById(Long id) {
        Amenities amenities = amenitiesRepository.getAmenitiesById(id)
                .orElseThrow(() -> new RuntimeException("Amenity with id=" + id + " not found."));
        AmenitiesDto amenitiesDto = AmenitiesMapper.toDto(amenities);
        List<BarberDto> barberDtoList = new ArrayList<>();
        for (Barber barber : amenities.getBarberList()) {
            barberDtoList.add(BarberMapper.toDto(barber));
        }
        amenitiesDto.setBarberDtoList(barberDtoList);
        return amenitiesDto;
    }

    @Override
    public Amenities getEntityById(Long id) {
        return amenitiesRepository.getAmenitiesById(id)
                .orElseThrow(() -> new RuntimeException("Amenity with id=" + id + " not found."));
    }

    @Override
    @Transactional
    public AmenitiesDto save(AmenitiesDto dto) {
        dto.setId(null);
        Optional<Amenities> amenity = amenitiesRepository.getAmenitiesByName(dto.getName().toLowerCase());
        if (amenity.isPresent()) {
            throw new RuntimeException("Amenity with name=" + dto.getName() + " already exists");
        }
        dto.setName(dto.getName().toLowerCase());
        Amenities savedAmenity = amenitiesRepository.save(AmenitiesMapper.toEntity(dto));
        return AmenitiesMapper.toDto(savedAmenity);
    }

    @Override
    @Transactional
    public AmenitiesDto update(AmenitiesDto dto) {
        Amenities amenities = amenitiesRepository.getAmenitiesById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Amenity with id " + dto.getId() + " not found!"));
        if (dto.getName() != null) {
            Optional<Amenities> optionalAmenities = amenitiesRepository.getAmenitiesByName(dto.getName().toLowerCase());
            if (optionalAmenities.isPresent()) {
                throw new RuntimeException("Amenity with name " + dto.getName() + " already exists");
            }
            amenities.setName(dto.getName().toLowerCase());
        }
        if (dto.getDescription() != null) {
            amenities.setDescription(dto.getDescription());
        }
        if(dto.getPrice() != null) {
            amenities.setPrice(dto.getPrice());
        }
        if(dto.getDuration() != null) {
            amenities.setDuration(dto.getDuration());
        }
        return AmenitiesMapper.toDto(amenitiesRepository.save(amenities));
    }

    @Override
    public List<AmenitiesDto> getAllDto() {
        List<Amenities> amenities = amenitiesRepository.findAll();
        return amenities.stream().map(AmenitiesMapper::toDto)
                .collect(Collectors.toList());
    }

    public Double getAvgPrice() {
        List<Amenities> amenities = amenitiesRepository.findAll();
        Double sumPrice = 0D;
        for (Amenities a : amenities) {
            sumPrice += a.getPrice();
        }
        return !amenities.isEmpty() ? sumPrice / amenities.size() : sumPrice;
    }

    @Transactional
    public void deleteAmenity(Long id) throws RuntimeException {
        Amenities amenities = amenitiesRepository.getAmenitiesById(id).orElseThrow(() -> new RuntimeException("Amenities with id = " + id + " didn't exists!"));
        for (Barber barber : amenities.getBarberList()) {
            barber.getAmenitiesList().remove(amenities);
        }
        amenities.setBarberList(new ArrayList<>());
        Amenities newAmenities = amenitiesRepository.save(amenities);
        amenitiesRepository.delete(newAmenities);
    }
}

package com.practice.barbershop.mapper;


import com.practice.barbershop.dto.AmenitiesDto;
import com.practice.barbershop.model.Amenities;

public class AmenitiesMapper {

    public static AmenitiesDto toDto(Amenities entity) {
        AmenitiesDto amenities = new AmenitiesDto();

        amenities.setId(entity.getId());
        amenities.setName(entity.getName());
        amenities.setPrice(entity.getPrice());

        return amenities;
    }

    public static Amenities toEntity(AmenitiesDto entity) {
        Amenities amenities = new Amenities();

        amenities.setId(entity.getId());
        amenities.setName(entity.getName());
        amenities.setPrice(entity.getPrice());

        return amenities;
    }
}

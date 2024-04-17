package com.practice.barbershop.mapper;


import com.practice.barbershop.dto.AmenitiesDto;
import com.practice.barbershop.model.Amenities;


/** Convert AmenitiesDto to Amenities and Amenities to AmenitiesDto
 * @author David
 */
public class AmenitiesMapper {

    /**
     * Convert Amenities to AmenitiesDto
     * @param entity Amenities
     * @return AmenitiesDto
     */
    public static AmenitiesDto toDto(Amenities entity) {
        AmenitiesDto amenities = new AmenitiesDto();

        amenities.setId(entity.getId());
        amenities.setName(entity.getName());
        amenities.setDescription(entity.getDescription());
        amenities.setPrice(entity.getPrice());
        amenities.setDuration(entity.getDuration());

        return amenities;
    }
    /**
     * Convert AmenitiesDto to Amenities
     * @param dto AmenitiesDto
     * @return Amenities
     */
    public static Amenities toEntity(AmenitiesDto dto) {
        Amenities amenities = new Amenities();

        amenities.setId(dto.getId());
        amenities.setName(dto.getName());
        amenities.setDescription(dto.getDescription());
        amenities.setPrice(dto.getPrice());
        amenities.setDuration(dto.getDuration());

        return amenities;
    }
}

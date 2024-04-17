package com.practice.barbershop.mapper;

import com.practice.barbershop.dto.PhotoDto;
import com.practice.barbershop.model.Barber;
import com.practice.barbershop.model.Photo;

/** Convert PhotoDto to Photo and Photo to PhotoDto
 * @author David
 */
public class PhotoMapper {
    /**
     * Convert PhotoDto to Photo
     * @param dto PhotoDto
     * @return Photo
     */
    public static Photo toEntity(PhotoDto dto) {
        Photo photo = new Photo();

        photo.setId(dto.getId());
        photo.setName(dto.getName());

        Barber barber = new Barber();
        barber.setId(dto.getBarberId());
        photo.setBarber(barber);

        return photo;
    }
    /**
     * Convert Photo to PhotoDto
     * @param entity Photo
     * @return PhotoDto
     */
    public static PhotoDto toDto(Photo entity) {
        PhotoDto photoDto = new PhotoDto();

        photoDto.setId(entity.getId());
        photoDto.setName(entity.getName());
        photoDto.setBarberId(entity.getBarber().getId());

        return photoDto;
    }
}

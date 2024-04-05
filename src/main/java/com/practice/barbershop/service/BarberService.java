package com.practice.barbershop.service;

import com.practice.barbershop.dto.AmenitiesDto;
import com.practice.barbershop.dto.BarberDto;
import com.practice.barbershop.dto.PhotoDto;
import com.practice.barbershop.general.MyService;
import com.practice.barbershop.mapper.BarberMapper;
import com.practice.barbershop.mapper.PhotoMapper;
import com.practice.barbershop.model.Barber;
import com.practice.barbershop.model.Photo;
import com.practice.barbershop.repository.BarberRepository;
import com.practice.barbershop.service.impl.PhotoServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    private final PhotoServiceImpl photoService;

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
        BarberDto barber = getDtoById(barberId);
        AmenitiesDto amenities = amenitiesService.getDtoById(amenityId);

        boolean contains = false;
        for (AmenitiesDto amenity : barber.getAmenitiesDtoList()) {
            if (Objects.equals(amenity.getId(), amenities.getId())) {
                contains = true;
                break;
            }
        }
        if (!contains) {
            barber.getAmenitiesDtoList().add(amenities);
            update(barber);
        } else {
            throw new RuntimeException("Amenity have already been added for this barber.");
        }
    }
    @Transactional
    public String uploadImage(String path, MultipartFile file, Long barberId) throws IOException {
        //Filename
        String name = file.getOriginalFilename();

        PhotoDto photoDto = new PhotoDto();
        photoDto.setBarberId(barberId);
        photoDto.setName(name);

        Photo photo = photoService.save(PhotoMapper.toEntity(photoDto));
        String photoName = photo.getId() + "_" + name;

        //Full path
        String filePath = path + File.separator + photoName;
        //Create folder if not created
        File f = new File(path);
        if (!f.exists()) {
            f.mkdir();
        }
        //File copy
        Files.copy(file.getInputStream(), Paths.get(filePath));
        return name;
    }
}

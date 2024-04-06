package com.practice.barbershop.service;

import com.practice.barbershop.dto.AmenitiesDto;
import com.practice.barbershop.dto.BarberDto;
import com.practice.barbershop.dto.PhotoDto;
import com.practice.barbershop.general.MyService;
import com.practice.barbershop.mapper.AmenitiesMapper;
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
        BarberDto barber = BarberMapper.toDto(barberRepository.getBarberById(id)
                .orElseThrow(() -> new RuntimeException("Barber with id=" + id + " not found.")));
        barber.getAmenitiesDtoList().forEach((AmenitiesDto a) -> a.setPrice((int) Math.floor(a.getPrice() * barber.getBarberDegree().getExtraCharge())));

        return barber;
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
        savedBarber.getAmenitiesList()
                .replaceAll(amenities -> amenitiesService.getEntityById(amenities.getId()));
        savedBarber.setPhotoList(photoService.getPhotoByBarberId(savedBarber.getId()));

        BarberDto finalDto = BarberMapper.toDto(savedBarber);
        finalDto.getAmenitiesDtoList().forEach((AmenitiesDto a) -> a.setPrice((int) Math.floor(a.getPrice() * dto.getBarberDegree().getExtraCharge())));
        return finalDto;
    }

    @Override
    @Transactional
    public BarberDto update(BarberDto dto) {

        Barber updatedBarber = getEntityById(dto.getId());

        if (dto.getAmenitiesDtoList() == null && updatedBarber.getAmenitiesList() != null) {
            dto.setAmenitiesDtoList(updatedBarber.getAmenitiesList().stream()
                    .map(AmenitiesMapper::toDto).collect(Collectors.toList()));
        }
        if (dto.getPhotoDtoList() == null && updatedBarber.getPhotoList() != null) {
            dto.setPhotoDtoList(updatedBarber.getPhotoList().stream()
                    .map(PhotoMapper::toDto).collect(Collectors.toList()));
        }

        updatedBarber = barberRepository.save(BarberMapper.toEntity(dto));
        updatedBarber.getAmenitiesList()
                .replaceAll(amenities -> amenitiesService.getEntityById(amenities.getId()));
        updatedBarber.setPhotoList(photoService.getPhotoByBarberId(updatedBarber.getId()));

        BarberDto finalDto = BarberMapper.toDto(updatedBarber);
        finalDto.getAmenitiesDtoList().forEach((AmenitiesDto a) -> a.setPrice((int) Math.floor(a.getPrice() * dto.getBarberDegree().getExtraCharge())));

        return finalDto;
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
    public void deleteAmenityFromBarber(Long barberId, Long amenityId) {
        BarberDto barber = getDtoById(barberId);
        AmenitiesDto amenities = amenitiesService.getDtoById(amenityId);

        boolean contains = false;
        for (AmenitiesDto amenity : barber.getAmenitiesDtoList()) {
            if (Objects.equals(amenity.getId(), amenities.getId())) {
                contains = true;
                break;
            }
        }
        if (contains) {
            barber.getAmenitiesDtoList().removeIf(amenitiesDto -> Objects.equals(amenitiesDto.getId(), amenityId));
            update(barber);
        } else {
            throw new RuntimeException("Amenity with id=" + amenityId + " not found for this barber.");
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

    @Transactional
    public String deleteImage(String path, String fileName, Long barberId) {
        File deleteFile = new File(path + "\\" + fileName);

        Barber barber = getEntityById(barberId);
        List<Photo> photos = barber.getPhotoList();

        for (Photo photo : photos) {
            if (Objects.equals(photo.toString(), fileName)) {
                photoService.delete(photo);
            }
        }
        photos.removeIf(photo -> Objects.equals(photo.toString(), fileName));

        barber.setPhotoList(photos);
        update(BarberMapper.toDto(barber));

        if (deleteFile.delete()) {
            return  "Deleted the file: " + deleteFile.getName();
        }

        return "Failed to delete the file.";
    }
}

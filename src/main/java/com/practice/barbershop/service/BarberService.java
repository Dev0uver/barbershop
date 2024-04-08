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
     * Get barber from db if it exists or else throw not found runtime exception.
     * Change amenity price via barbers degree.
     * Return barber dto.
     * @param id barber id
     * @return Barber dto
     */
    @Override
    public BarberDto getDtoById(Long id) {

        BarberDto barber = BarberMapper.toDto(barberRepository.getBarberById(id)
                .orElseThrow(() -> new RuntimeException("Barber with id=" + id + " not found.")));

        barber.getAmenitiesDtoList().forEach(
                (AmenitiesDto a) ->
                        a.setPrice((int) Math.floor(a.getPrice() * barber.getBarberDegree().getExtraCharge()))
        );

        return barber;
    }

    /**
     * Get barber from db if it exists or else throw not found runtime exception. Return barber entity.
     * @param id entity id
     * @return Barber entity
     */
    @Override
    public Barber getEntityById(Long id) {
        return barberRepository.getBarberById(id)
                .orElseThrow(() -> new RuntimeException("Barber with id=" + id + " not found."));
    }

    /**
     * Save entity in db. Set barber id=<code>null</code>.
     * Save barber into db and get this new barber entity with id.
     * Set amenity entity and photo entity lists to barber.
     * Convert barber to dto.
     * Change amenity price via barbers degree.
     * Return barber dto.
     * @param dto BarberDto object
     * @return saved BarberDto object
     */
    @Override
    @Transactional
    public BarberDto save(BarberDto dto) {

        dto.setId(null);
        Long savedBarberId = barberRepository.save(BarberMapper.toEntity(dto)).getId();
        Barber savedBarber = getEntityById(savedBarberId);

        savedBarber.getAmenitiesList()
                .replaceAll(amenities -> amenitiesService.getEntityById(amenities.getId()));
        savedBarber.setPhotoList(photoService.getPhotoByBarberId(savedBarber.getId()));

        BarberDto finalDto = BarberMapper.toDto(savedBarber);

        finalDto.getAmenitiesDtoList().forEach(
                (AmenitiesDto a) ->
                        a.setPrice((int) Math.floor(a.getPrice() * dto.getBarberDegree().getExtraCharge()))
        );
        return finalDto;
    }

    /**
     * Trying to get this barber from db via id
     * if barber not exists throw new not found runtime exception.
     * if dto amenity and photo lists != null,
     * then convert updated barber amenity and photo entities lists
     * to dto and set to dto object. Update barber into db.
     * Set to updated barber amenity entity and photo entity lists.
     * convert updated barber to dto
     * Change amenity price via barbers degree.
     * Return barber dto.
     * @param dto BarberDto object
     * @return updated BarberDto object
     */
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
        updatedBarber.setPhotoList(photoService.getPhotoByBarberId(updatedBarber.getId()));

        BarberDto finalDto = BarberMapper.toDto(updatedBarber);
        finalDto.getAmenitiesDtoList().forEach(
                (AmenitiesDto a) ->
                        a.setPrice((int) Math.floor(a.getPrice() * dto.getBarberDegree().getExtraCharge()))
        );
        return finalDto;
    }

    /**
     * Get all Barbers from db
     * @return List<Barber>
     */
    @Override
    public List<BarberDto> getAllDto() {
        List<Barber> barbers = barberRepository.findAll();
        return barbers.stream().map(BarberMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Add Amenity to barber via barber id and amenity id.
     * trying to get amenity. If not found run new not found runtime exception.
     * Then check barber amenities list not contains amenity or else run new runtime exception.
     * If barber not contains this amenity. Add amenity to list and update barber
     * @param barberId ID of barber
     * @param amenityId ID of amenity
     */
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
    /**
     * Delete Amenity from barber via barber id and amenity id.
     * trying to get amenity. If not found throw new not found runtime exception.
     * Then check barber amenities list contains amenity or else run new runtime exception.
     * If barber contains this amenity. Remove amenity from list and update barber
     * @param barberId ID of barber
     * @param amenityId ID of amenity
     */
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

    /**
     * Link img to barber. Get original file name.
     * Create new photo dto object, set barber id and file name.
     * Trying to save converted to entity photo object.
     * Get id of record. Make new file name via photo id and "_" and original file name.
     * Make new path. Check folder exists. Create new file.
     * @param path path in hard catalog.
     * @param file Photo
     * @param barberId Barber
     * @return answer
     * @throws IOException exception
     */
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

    /**
     * Delete photo from db and local disk.
     * @param path path in hard catalog.
     * @param fileName Name of img
     * @param barberId Barber id.
     * @return Answer
     */
    @Transactional
    public String deleteImage(String path, String fileName, Long barberId) {
        File deleteFile = new File(path + "\\" + fileName);

        Barber barber = getEntityById(barberId);
        List<Photo> photos = barber.getPhotoList();

        boolean contains = false;
        for (Photo photo : photos) {
            if (Objects.equals(photo.toString(), fileName)) {
                photoService.delete(photo);
                contains = true;
            }
        }
        if (!contains) {
            throw new RuntimeException("This barber doesn't have this photo.");
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

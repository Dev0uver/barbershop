package com.practice.barbershop.service.impl;

import com.practice.barbershop.model.Photo;
import com.practice.barbershop.repository.PhotoRepository;
import com.practice.barbershop.service.PhotoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class PhotoServiceImpl implements PhotoService {
    private final PhotoRepository photoRepository;
    @Override
    @Transactional
    public Photo save(Photo photo) {
        photo.setId(null);
        return photoRepository.save(photo);
    }

    @Override
    @Transactional
    public void delete(Photo photo) {
        photoRepository.delete(photo);
    }

    @Override
    @Transactional
    public void update(Photo photo) {
        if (photo.getId() == null) {
            throw new RuntimeException("Id must be not null");
        }
        photoRepository.save(photo);
    }

    @Override
    public List<Photo> getPhotoByBarberId(Long id) {
        return photoRepository.findAllByBarberId(id);
    }
}

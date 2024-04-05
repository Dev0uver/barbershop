package com.practice.barbershop.service;

import com.practice.barbershop.model.Photo;

import java.util.List;

public interface PhotoService {

    Photo save(Photo photo);

    void delete(Photo photo);

    void update(Photo photo);

    List<Photo> getPhotoByBarberId(Long id);



}

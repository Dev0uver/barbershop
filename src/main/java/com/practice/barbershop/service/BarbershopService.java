package com.practice.barbershop.service;

import com.practice.barbershop.dto.BarbershopDto;
import com.practice.barbershop.model.Barbershop;

import java.util.List;

public interface BarbershopService {

    void createBarbershop(BarbershopDto barbershopDto);

    BarbershopDto getBarbershop(Long id);

    boolean updateBarbershop(Long id, BarbershopDto barbershopDto);

    boolean deleteBarbershop(Long id);

    void updateSchedule();

    List<Barbershop> getAllBarberShops();
}

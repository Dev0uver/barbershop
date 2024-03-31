package com.practice.barbershop.service.patern;

import com.practice.barbershop.dto.BarbershopDto;

public interface BarbershopService {

    void createBarbershop(BarbershopDto barbershopDto);

    BarbershopDto getBarbershop(Long id);

    boolean updateBarbershop(Long id, BarbershopDto barbershopDto);

    boolean deleteBarbershop(Long id);
}

package com.practice.barbershop.service;

import com.practice.barbershop.dto.BarbershopDto;

public interface BarbeshopService {

    public void createBarbershop(BarbershopDto barbershopDto);

    public BarbershopDto getBarbershop(Long id);

    public boolean updateBarbershop(Long id, BarbershopDto barbershopDto);

    public boolean deleteBarbershop(Long id);
}

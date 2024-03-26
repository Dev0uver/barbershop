package com.practice.barbershop.service;

import com.practice.barbershop.dto.BarbershopDto;
import com.practice.barbershop.mapper.BarbershopMapper;
import com.practice.barbershop.model.BarbershopEntity;
import com.practice.barbershop.repository.BarbershopRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@AllArgsConstructor
public class BarbershopServiceImpl implements BarbeshopService {

    private final BarbershopRepository barbershopRepository;
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public void createBarbershop(BarbershopDto barbershopDto) {
        BarbershopEntity barbershopEntity = BarbershopMapper.toDto(barbershopDto);
        barbershopRepository.save(barbershopEntity);
    }

    @Override
    public BarbershopDto getBarbershop(Long id) {
        Optional<BarbershopEntity> optionalBarbershopEntity = barbershopRepository.findById(id);
        if (optionalBarbershopEntity.isPresent()) {
            BarbershopDto barbershopDto = this.modelMapper.map(optionalBarbershopEntity.get(), BarbershopDto.class);
            barbershopDto.setSchedule(BarbershopMapper.orderSchedule(barbershopDto.getSchedule()));
            return barbershopDto;
        }
        else {
            return null;
        }
    }

    @Transactional
    @Override
    public boolean updateBarbershop(Long id, BarbershopDto barbershopDto) {
        Optional<BarbershopEntity> optionalBarbershopEntity = barbershopRepository.findById(id);
        if (optionalBarbershopEntity.isPresent()) {
            BarbershopEntity barbershopEntity = optionalBarbershopEntity.get();
            barbershopEntity.setAddress(barbershopDto.getAddress());
            barbershopEntity.setContactPhone(barbershopDto.getContactPhone());
            barbershopEntity.setContactEmail(barbershopDto.getContactEmail());
            barbershopEntity.setAverageRating(barbershopDto.getAverageRating());
            barbershopEntity.setAverageServiceCost(barbershopDto.getAverageServiceCost());
            barbershopDto.setSchedule(BarbershopMapper.orderSchedule(barbershopDto.getSchedule()));
            barbershopEntity.setSchedule(barbershopDto.getSchedule());

            barbershopRepository.save(barbershopEntity);
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public boolean deleteBarbershop(Long id) {
        Optional<BarbershopEntity> optionalBarbershopEntity = barbershopRepository.findById(id);
        if (optionalBarbershopEntity.isPresent()) {
            barbershopRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

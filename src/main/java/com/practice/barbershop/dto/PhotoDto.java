package com.practice.barbershop.dto;

import lombok.Data;

@Data
public class PhotoDto {
    private Long id;
    private String name;
    private Long barberId;
}

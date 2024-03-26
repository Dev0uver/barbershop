package com.practice.barbershop.controller;

import com.practice.barbershop.dto.BarbershopDto;
import com.practice.barbershop.service.BarbershopServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/barbershop")
@AllArgsConstructor
public class BarbershopController {

    public final BarbershopServiceImpl barbershopServiceImpl;

    @PostMapping(value = "/create", consumes="application/json")
    public ResponseEntity<?> newBarbershop(@RequestBody BarbershopDto barbershopDto) {
        barbershopServiceImpl.createBarbershop(barbershopDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Barbershop created successfully!");
    }

    @GetMapping(value = "/viewInfo/{id}", produces="application/json")
    public ResponseEntity<?> viewBarbershopInfo(@PathVariable Long id) {
        BarbershopDto barbershopDto = barbershopServiceImpl.getBarbershop(id);
        if (barbershopDto != null) {
            return ResponseEntity.status(HttpStatus.OK).body(barbershopDto);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND");
        }
    }

    @PutMapping(value = "/update/{id}", consumes = "application/json")
    public ResponseEntity<?> updateBarbershop(@PathVariable Long id, @RequestBody BarbershopDto barbershopDto) {
        boolean updated = barbershopServiceImpl.updateBarbershop(id, barbershopDto);
        if (updated) {
            return ResponseEntity.status(HttpStatus.OK).body("Barbershop updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Barbershop not found");
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteBarbershop(@PathVariable Long id) {
        boolean deleted = barbershopServiceImpl.deleteBarbershop(id);
        if (deleted) {
            return ResponseEntity.status(HttpStatus.OK).body("Barbershop deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Barbershop not found");
        }
    }
}

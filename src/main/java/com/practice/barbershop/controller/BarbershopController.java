package com.practice.barbershop.controller;

import com.practice.barbershop.dto.BarbershopDto;
import com.practice.barbershop.service.impl.BarbershopServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/** Responds to requests for barbershop
 * @author David
 */
@RestController
@RequestMapping("/barbershop")
@AllArgsConstructor
public class BarbershopController {

    public final BarbershopServiceImpl barbershopServiceImpl;

    /**
     * RequestMethod=POST. Accepts the DTO of the barbershop as a request body.
     * Saves a new barbershop
     * @param barbershopDto The barbershop dto
     * @return ResponseEntity
     */
    @PostMapping(value = "/create", consumes="application/json")
    public ResponseEntity<?> newBarbershop(@RequestBody BarbershopDto barbershopDto) {
        barbershopServiceImpl.createBarbershop(barbershopDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Barbershop created successfully!");
    }

    /**
     * RequestMethod=GET. Checks the existence of a barbershop.
     * Displays information about the barbershop
     * @param id The barbershop's id
     * @return ResponseEntity
     */
    @GetMapping(value = "/viewInfo/{id}", produces="application/json")
    public ResponseEntity<?> viewBarbershopInfo(@PathVariable Long id) {
        try {
            BarbershopDto barbershopDto = barbershopServiceImpl.getBarbershop(id);
            return ResponseEntity.status(HttpStatus.OK).body(barbershopDto);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * RequestMethod=PUT. Checks the existence of a barbershop.
     * Updates information about the barbershop including the schedule.
     * @param id The barbershop's id
     * @param barbershopDto New barbershop information in dto
     * @return ResponseEntity
     */
    @PutMapping(value = "/update/{id}", consumes = "application/json")
    public ResponseEntity<?> updateBarbershop(@PathVariable Long id, @RequestBody BarbershopDto barbershopDto) {
        boolean updated = barbershopServiceImpl.updateBarbershop(id, barbershopDto);
        if (updated) {
            return ResponseEntity.status(HttpStatus.OK).body("Barbershop updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Barbershop not found");
        }
    }

    /**
     * RequestMethod=DELETE. Checks the existence of a barbershop.
     * Delete information about the barbershop.
     * @param id The barbershop's id
     * @return ResponseEntity
     */
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

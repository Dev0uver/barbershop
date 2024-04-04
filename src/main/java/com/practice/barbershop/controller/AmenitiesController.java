package com.practice.barbershop.controller;

import com.practice.barbershop.dto.AmenitiesDto;
import com.practice.barbershop.service.AmenitiesService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Responds to requests for amenity
 * @author David
 */
@RestController
@AllArgsConstructor
@RequestMapping("/amenity")
public class AmenitiesController {
    private final AmenitiesService amenitiesService;


    /**
     * RequestMethod=POST. Accepts the DTO of the amenity as a request body.
     * Checks whether this amenity already exists in the database.
     * Saves a new amenity
     * @param amenity The amenity dto
     * @return ResponseEntity
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> addAmenity(@RequestBody AmenitiesDto amenity) {
        try {
            AmenitiesDto amenitiesDto = amenitiesService.save(amenity);
            return ResponseEntity.status(HttpStatus.CREATED).body(amenitiesDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * RequestMethod=GET. Calculates the average cost of all existing amenities.
     * @return average cost of all amenities
     */
    @RequestMapping(value = "/avg", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getAvgPrice() {
        return ResponseEntity.status(HttpStatus.OK).body("Avg price of amenities=" + amenitiesService.getAvgPrice());
    }

}

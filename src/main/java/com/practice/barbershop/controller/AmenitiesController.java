package com.practice.barbershop.controller;

import com.practice.barbershop.dto.AmenitiesDto;
import com.practice.barbershop.service.AmenitiesService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping( "/getById/{id}")
    public ResponseEntity<?> getAmenityById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(amenitiesService.getDtoById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        List<AmenitiesDto> amenitiesDtoList = amenitiesService.getAllDto();
        if (amenitiesDtoList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is not a single amenity!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(amenitiesDtoList);
    }

    @PutMapping("/updateAmenity/{id}")
    public ResponseEntity<?> updateAmenity(@RequestBody AmenitiesDto amenity, @PathVariable Long id) {
        try {
            amenity.setId(id);
            return ResponseEntity.status(HttpStatus.OK).body(amenitiesService.update(amenity));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @DeleteMapping("/deleteAmenity/{id}")
    public ResponseEntity<?> deleteAmenity(@PathVariable Long id) {
        try {
            amenitiesService.deleteAmenity(id);
            return ResponseEntity.status(HttpStatus.OK).body("Amenity deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
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

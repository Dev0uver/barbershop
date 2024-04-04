package com.practice.barbershop.controller;

import com.practice.barbershop.dto.BarberDto;
import com.practice.barbershop.general.BarberStatus;
import com.practice.barbershop.service.BarberService;
import com.practice.barbershop.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/** Responds to requests for barbers
 * @author David
 */
@RestController
@RequestMapping(value = "/barber")
@AllArgsConstructor
public class BarberController {

    private final BarberService barberService;
    private final OrderService orderService;

    @Value("${project.image}")
    private static String path;

    /**
     * RequestMethod=POST. Accepts the DTO of the barber as a request body.
     * Saves a new barber
     * @param barber The barber dto
     * @return ResponseEntity
     */
    //TODO Исправить создание новых барберов
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> createBarber(@RequestBody BarberDto barber) {
        try {
            BarberDto savedBarber = barberService.save(barber);
            return ResponseEntity.status(HttpStatus.OK).body(savedBarber);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * RequestMethod=PUT. Accepts the status of the barber as a parameter.
     * Checks whether this barber already exists in the database.
     * Update the barber
     * @param id Barber's ID
     * @param status new status for Barber
     * @return ResponseEntity
     */
    @RequestMapping(value = "/change", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> changeStatus (
            @RequestParam(name = "id") Long id,
            @RequestParam(name = "status") String status
            ) {
        try {
            BarberDto barber = barberService.getDtoById(id);
            barber.setBarberStatus(BarberStatus.valueOf(status.toUpperCase()));
            barberService.update(barber);
            return ResponseEntity.status(HttpStatus.OK).body(barber);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    /**
     * RequestMethod=GET. Calculates the average mark of all existing orders for current barber.
     * @param barberId The barber's ID
     * @return average cost of all amenities
     */
    @RequestMapping(value = "/average-rating", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> averageRating(@RequestParam(name = "barberId") Long barberId) {
        try {
            BarberDto barberDto = barberService.getDtoById(barberId);

            Double averageMark = orderService.avgRate(barberDto);

            return ResponseEntity.status(HttpStatus.OK).body("Avg. rating=" + averageMark + " for barber with id=" + barberDto.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @RequestMapping(value = "/addAmenity", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> addAmenity(@RequestParam(name = "amenityId") Long amenityId,
                                        @RequestParam(name = "barberId") Long barberId) {
        try {
            barberService.addAmenityToBarber(barberId, amenityId);
            return ResponseEntity.status(HttpStatus.OK).body("Amenity added");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @RequestMapping(value = "/loadPhoto", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> addBarbersPhoto(@RequestParam(name = "photo") MultipartFile photo) {
        try {
            String fileName = barberService.uploadImage(path, photo);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Photo with name=" + fileName + " successfully added");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}

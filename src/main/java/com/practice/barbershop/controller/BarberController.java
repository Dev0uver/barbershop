package com.practice.barbershop.controller;

import com.practice.barbershop.dto.BarberDto;
import com.practice.barbershop.general.BarberDegree;
import com.practice.barbershop.general.BarberStatus;
import com.practice.barbershop.service.BarberService;
import com.practice.barbershop.service.OrderService;
import lombok.AllArgsConstructor;
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


    private static final String path = "src/main/resources/photo";

    /**
     * RequestMethod=POST. Accepts the DTO of the barber as a request body.
     * Saves a new barber
     * @param barber The barber dto
     * @return ResponseEntity
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> createBarber(@RequestBody BarberDto barber) {
        try {
            BarberDto savedBarber = barberService.save(barber);
//            savedBarber.getAmenitiesDtoList().forEach((AmenitiesDto a) -> a.setPrice((int) Math.floor(a.getPrice() * savedBarber.getBarberDegree().getExtraCharge())));
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
    @RequestMapping(value = "/change-status", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> changeStatus (
            @RequestParam(name = "id") Long id,
            @RequestParam(name = "status") String status
            ) {
        try {
            BarberDto barber = barberService.getDtoById(id);
            barber.setBarberStatus(BarberStatus.valueOf(status.toUpperCase()));
            barber = barberService.update(barber);
//            barber.getAmenitiesDtoList().forEach((AmenitiesDto a) -> a.setPrice((int) Math.floor(a.getPrice() * barber.getBarberDegree().getExtraCharge())));            return ResponseEntity.status(HttpStatus.OK).body(barber);
            return ResponseEntity.status(HttpStatus.OK).body(barber);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @RequestMapping(value = "/change-degree", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> changeDegree (
            @RequestParam(name = "id") Long id,
            @RequestParam(name = "degree") String degree
    ) {
        try {
            BarberDto barber = barberService.getDtoById(id);
            barber.setBarberDegree(BarberDegree.valueOf(degree.toUpperCase()));
            barber = barberService.update(barber);
//            barber.getAmenitiesDtoList().forEach((AmenitiesDto a) -> a.setPrice((int) Math.floor(a.getPrice() * barber.getBarberDegree().getExtraCharge())));
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

    @RequestMapping(value = "/deleteAmenity", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<?> deleteAmenity(@RequestParam(name = "amenityId") Long amenityId,
                                        @RequestParam(name = "barberId") Long barberId) {
        try {
            barberService.deleteAmenityFromBarber(barberId, amenityId);
            return ResponseEntity.status(HttpStatus.OK).body("Amenity deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/loadPhoto", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> addBarbersPhoto(@RequestParam(name = "photo") MultipartFile photo,
                                             @RequestParam(name = "barberId") Long barberId) {
        try {
            String fileName = barberService.uploadImage(path, photo, barberId);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Photo with name=" + fileName + " successfully added");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/deletePhoto", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<?> deletePhoto(@RequestParam(name = "photoName") String photoName,
                                         @RequestParam(name = "barberId") Long barberId) {
        try {
            String response = barberService.deleteImage(path, photoName, barberId);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> info(@RequestParam(name = "barberId") Long barberId) {
        try {
            BarberDto barberDto = barberService.getDtoById(barberId);
            return ResponseEntity.status(HttpStatus.OK).body(barberDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/barber-orders", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getOrdersByBarber(@RequestParam(name = "barberId") Long barberId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrdersByBarberId(barberId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/update-barber", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> updateBarber(@RequestBody BarberDto barber) {
        try {

            return ResponseEntity.status(HttpStatus.OK).body(barberService.update(barber));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}

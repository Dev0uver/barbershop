package com.practice.barbershop.controller;

import com.practice.barbershop.dto.BarberDto;
import com.practice.barbershop.dto.OrderDto;
import com.practice.barbershop.dto.RegistrationsDto;
import com.practice.barbershop.service.BarberService;
import com.practice.barbershop.service.OrderService;
import com.practice.barbershop.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final RegistrationService registrationService;
    private final BarberService barberService;
    @RequestMapping(value = "/create/reg", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> createOrderForRegistration(@RequestParam(name = "regId") Long regId) {

        try {
            RegistrationsDto registration = registrationService.getDtoById(regId);
            if (!registration.getCanceled()) {
                OrderDto savedOrder = orderService.save(orderService.createOrderByReg(registration));
                return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registration with id=" + regId +" canceled");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/mark", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> markOrder(@RequestParam(name = "orderId") Long orderId,
                                       @RequestParam(name = "mark") Byte mark) {
        if (mark < 0 || mark > 5) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mark must be between 0 and 5");
        }
        try {
            OrderDto orderDto = orderService.getDtoById(orderId);
            orderDto.setMark(mark);
            orderService.update(orderDto);
            return ResponseEntity.status(HttpStatus.OK).body("Mark=" + mark + " has been successfully made");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

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
    @RequestMapping(value = "/add-amenity", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> addAmenities(@RequestParam(name = "amenityId") Long amenityId,
                                          @RequestParam(name = "orderId") Long orderId) {
        try {
            orderService.addAmenity(orderId,amenityId);
            return ResponseEntity.status(HttpStatus.OK).body("Amenity added");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}

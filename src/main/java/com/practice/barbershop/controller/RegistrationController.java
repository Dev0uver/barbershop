package com.practice.barbershop.controller;

import com.practice.barbershop.dto.RegistrationsDto;
import com.practice.barbershop.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;

/** Responds to requests for registrations
 * @author David
 */
@RestController
@AllArgsConstructor
@RequestMapping("/reg")
public class RegistrationController {

    private final RegistrationService registrationService;

    /**
     * RequestMethod=POST. Accepts the DTO of the registration as a request body.
     * Checks the existence of a record for a given date, time, and barber.
     * Saves a new registration
     * @param registrationsDto The registration dto
     * @return ResponseEntity
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> register(
              @RequestBody RegistrationsDto registrationsDto) {
        try {
            RegistrationsDto registrations = registrationService.save(registrationsDto);
            return ResponseEntity.status(HttpStatus.OK).body(registrations);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    /**
     * RequestMethod=PUT. Accepts the time, day, barberId of the registration as a parameters.
     * Checks whether this registration on these parameters already exists in the database.
     * Cancel the registration.
     * @param time The time of registration
     * @param day The date of registration
     * @param barberId The barber's ID
     * @return ResponseEntity
     */
    @RequestMapping(value = "/delete", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?>  cancel(
            @RequestParam(name = "time") LocalTime time,
            @RequestParam(name = "day") LocalDate day,
            @RequestParam(name = "barberId") Long barberId) {
        try {
            RegistrationsDto registration = registrationService.canceled(time,day,barberId);
            return ResponseEntity.status(HttpStatus.OK).body(registration);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    /**
     * RequestMethod=POST. Accepts the time, day, barberId of the registration as a parameters.
     * Checks whether this registration on these parameters exists in the database.
     * If not Cancel return the registration.
     * @param time The time of registration
     * @param day The date of registration
     * @param barberId The barber's ID
     * @return ResponseEntity
     */
    @RequestMapping(value = "/read", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?>  readRegistration(
            @RequestParam(name = "time") LocalTime time,
            @RequestParam(name = "day") LocalDate day,
            @RequestParam(name = "barberId") Long barberId) {
        try {
            RegistrationsDto registration = registrationService.getByTimeAndDayAndBarberId(time,day,barberId);
            return ResponseEntity.status(HttpStatus.OK).body(registration);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}

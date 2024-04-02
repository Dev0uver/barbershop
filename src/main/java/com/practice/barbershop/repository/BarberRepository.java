package com.practice.barbershop.repository;


import com.practice.barbershop.model.Barber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BarberRepository extends JpaRepository<Barber, Long> {


    /**
     * Function for getting Barber via ID. Get without collections.
     * @param id ID of barber
     * @return Barber entity
     */
    Optional<Barber> getBarberById(Long id);

    //TODO Разобраться почему FETCH не работает
//    @Query("SELECT b FROM Barber b JOIN FETCH b.orderList WHERE b.id = ?1")
//    Optional<Barber> getBarberByIdAllCollections(Long id);

}

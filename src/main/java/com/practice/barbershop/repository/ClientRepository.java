package com.practice.barbershop.repository;

import com.practice.barbershop.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    /**
     * Function for getting Client via ID. Get without collections.
     * @param id ID of Client
     * @return Client entity
     */
    @Query("SELECT c FROM Client c WHERE c.id = ?1")
    Optional<Client> getClientById(Long id);

}

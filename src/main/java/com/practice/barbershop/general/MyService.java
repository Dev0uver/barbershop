package com.practice.barbershop.general;


import java.util.List;

/**
 * This is interface witch represents general logic of service
 * @author David
 * @param <D> dto
 * @param <E> entity
 */
public interface MyService<D,E> {


    /**
     * Get dto from database
     * @param id dto id
     * @return Dto
     */
    D getDtoById(Long id);

    /**
     * Get entity from database
     * @param id entity id
     * @return Entity
     */
    E getEntityById(Long id);

    /**
     * Save into database
     * @param dto object
     */
    D save(D dto);

    /**
     * Checks the existence of a record. Updating the database
     * @param dto object
     */
    D update(D dto);

    /**
     * Get list of dto
     * @return List of dto
     */
    List<D> getAllDto();

}

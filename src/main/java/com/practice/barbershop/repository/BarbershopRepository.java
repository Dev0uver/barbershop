package com.practice.barbershop.repository;

import com.practice.barbershop.model.Barbershop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BarbershopRepository extends JpaRepository<Barbershop, Long> {
}

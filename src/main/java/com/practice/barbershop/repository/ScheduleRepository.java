package com.practice.barbershop.repository;

import com.practice.barbershop.general.MyDayOfWeek;
import com.practice.barbershop.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findAllByBarbershopEntityId(Long barbershopEntity_id);

    Optional<Schedule> findByDayOfWeekAndBarbershopEntityId(MyDayOfWeek dayOfWeek, Long barbershopEntity_id);
}

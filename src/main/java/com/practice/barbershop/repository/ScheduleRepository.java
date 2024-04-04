package com.practice.barbershop.repository;

import com.practice.barbershop.general.MyDayOfWeek;
import com.practice.barbershop.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findAllByBarbershopId(Long barbershopEntity_id);

    Optional<Schedule> findByDayOfWeekAndBarbershopId(MyDayOfWeek dayOfWeek, Long barbershopEntity_id);
}

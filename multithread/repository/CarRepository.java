package com.multithreading.multithread.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.multithreading.multithread.entity.Cars;

public interface CarRepository extends JpaRepository<Cars, Long> {

}

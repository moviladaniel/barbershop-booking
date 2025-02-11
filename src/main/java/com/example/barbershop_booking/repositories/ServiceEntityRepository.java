package com.example.barbershop_booking.repositories;

import com.example.barbershop_booking.entities.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ServiceEntityRepository extends JpaRepository<ServiceEntity, Long> {

    List<ServiceEntity> findByDurationLessThan(int duration);

    List<ServiceEntity> findByPriceLessThan(double price);
}

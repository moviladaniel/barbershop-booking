package com.example.barbershop_booking.controllers;

import com.example.barbershop_booking.entities.ServiceEntity;
import com.example.barbershop_booking.services.ServiceEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
public class ServiceEntityController {

    @Autowired
    private ServiceEntityService serviceEntityService;

    @PostMapping
    public ResponseEntity<ServiceEntity> createService(@RequestBody ServiceEntity serviceEntity) {
        ServiceEntity saved = serviceEntityService.createService(serviceEntity);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<ServiceEntity>> getAllServices() {
        List<ServiceEntity> services = serviceEntityService.getAllServices();
        return ResponseEntity.ok(services);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceEntity> getServiceById(@PathVariable Long id) {
        ServiceEntity service = serviceEntityService.getServiceById(id);
        return (service != null) ? ResponseEntity.ok(service) : ResponseEntity.notFound().build();
    }

    @GetMapping("/byDurationLessThan")
    public List<ServiceEntity> getServicesByDurationLessThan(@RequestParam int duration) {
        return serviceEntityService.getServicesByDurationLessThan(duration);
    }

    @GetMapping("/byPriceLessThan")
    public List<ServiceEntity> getServicesByPriceLessThan(@RequestParam double price) {
        return serviceEntityService.getServicesByPriceLessThan(price);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceEntity> updateService(@PathVariable Long id, @RequestBody ServiceEntity updated) {
        ServiceEntity service = serviceEntityService.updateService(id, updated);
        return (service != null) ? ResponseEntity.ok(service) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        boolean deleted = serviceEntityService.deleteService(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}

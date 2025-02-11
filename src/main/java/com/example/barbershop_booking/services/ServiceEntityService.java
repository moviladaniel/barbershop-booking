package com.example.barbershop_booking.services;

import com.example.barbershop_booking.entities.ServiceEntity;
import com.example.barbershop_booking.repositories.ServiceEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceEntityService {

    @Autowired
    private ServiceEntityRepository serviceEntityRepository;

    public ServiceEntity createService(ServiceEntity serviceEntity) {
        return serviceEntityRepository.save(serviceEntity);
    }

    public List<ServiceEntity> getAllServices() {
        return serviceEntityRepository.findAll();
    }

    public ServiceEntity getServiceById(Long id) {
        return serviceEntityRepository.findById(id).orElse(null);
    }

    public List<ServiceEntity> getServicesByDurationLessThan(int duration) {
        return serviceEntityRepository.findByDurationLessThan(duration);
    }

    public List<ServiceEntity> getServicesByPriceLessThan(double price) {
        return serviceEntityRepository.findByPriceLessThan(price);
    }

    public ServiceEntity updateService(Long id, ServiceEntity updatedService) {
        ServiceEntity existingService = serviceEntityRepository.findById(id).orElse(null);
        if (existingService == null) {
            return null;
        }
        existingService.setName(updatedService.getName());
        existingService.setDuration(updatedService.getDuration());
        existingService.setPrice(updatedService.getPrice());
        return serviceEntityRepository.save(existingService);
    }

    public boolean deleteService(Long id) {
        if (!serviceEntityRepository.existsById(id)) {
            return false;
        }
        serviceEntityRepository.deleteById(id);
        return true;
    }
}

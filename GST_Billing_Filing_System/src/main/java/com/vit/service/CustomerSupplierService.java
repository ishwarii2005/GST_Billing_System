package com.vit.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vit.entity.CustomerSupplier;
import com.vit.exception.ResourceNotFoundException;
import com.vit.repository.CustomerSupplierRepository;

@Service
public class CustomerSupplierService {
    private final CustomerSupplierRepository repository;

    public CustomerSupplierService(CustomerSupplierRepository repository) {
        this.repository = repository;
    }

    public CustomerSupplier save(CustomerSupplier cs) {
        return repository.save(cs);
    }

    public List<CustomerSupplier> getByUserAndType(Long userId, CustomerSupplier.Type type) {
        return repository.findByUserIdAndType(userId, type);
    }

    public CustomerSupplier getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer/Supplier not found"));
    }
    
    public CustomerSupplier update(Long id, CustomerSupplier updated) {
        CustomerSupplier existing = getById(id);
        existing.setName(updated.getName());
        existing.setGstin(updated.getGstin());
        existing.setAddress(updated.getAddress());
        existing.setState(updated.getState());
        existing.setContact(updated.getContact());
        existing.setType(updated.getType());
        return repository.save(existing);
    }

    public void delete(Long id) {
        CustomerSupplier existing = getById(id);
        repository.delete(existing);
    }

}
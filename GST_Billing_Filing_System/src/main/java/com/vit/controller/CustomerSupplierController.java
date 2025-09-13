package com.vit.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vit.entity.CustomerSupplier;
import com.vit.service.CustomerSupplierService;

@RestController
@RequestMapping("/api/customers-suppliers")
public class CustomerSupplierController {

    private final CustomerSupplierService service;

    public CustomerSupplierController(CustomerSupplierService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CustomerSupplier> create(@RequestBody CustomerSupplier cs) {
        return ResponseEntity.ok(service.save(cs));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerSupplier> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CustomerSupplier>> getByUser(@PathVariable Long userId,
                                                            @RequestParam CustomerSupplier.Type type) {
        return ResponseEntity.ok(service.getByUserAndType(userId, type));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<CustomerSupplier> update(@PathVariable Long id,
                                                   @RequestBody CustomerSupplier cs) {
        return ResponseEntity.ok(service.update(id, cs));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
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
import org.springframework.web.bind.annotation.RestController;

import com.vit.entity.SalesInvoice;
import com.vit.service.SalesInvoiceService;

@RestController
@RequestMapping("/api/sales")
public class SalesInvoiceController {

    private final SalesInvoiceService service;

    public SalesInvoiceController(SalesInvoiceService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<SalesInvoice> create(@RequestBody SalesInvoice invoice) {
        return ResponseEntity.ok(service.save(invoice));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SalesInvoice>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getByUser(userId));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<SalesInvoice> update(@PathVariable Long id, @RequestBody SalesInvoice invoice) {
        return ResponseEntity.ok(service.update(id, invoice));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
package com.vit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vit.entity.CustomerSupplier;

public interface CustomerSupplierRepository extends JpaRepository<CustomerSupplier, Long> {
    List<CustomerSupplier> findByUserIdAndType(Long userId, CustomerSupplier.Type type);
}
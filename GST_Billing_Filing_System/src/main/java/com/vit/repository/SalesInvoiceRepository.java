package com.vit.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vit.entity.SalesInvoice;

public interface SalesInvoiceRepository extends JpaRepository<SalesInvoice, Long> {
    List<SalesInvoice> findByUserId(Long userId);
    List<SalesInvoice> findByUserIdAndDateBetween(Long userId, LocalDate start, LocalDate end);
}
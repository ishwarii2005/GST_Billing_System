package com.vit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vit.entity.SalesItem;

public interface SalesItemRepository extends JpaRepository<SalesItem, Long> {
}
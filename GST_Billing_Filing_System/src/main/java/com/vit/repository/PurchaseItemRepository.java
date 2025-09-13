package com.vit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vit.entity.PurchaseItem;

public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, Long> {
}
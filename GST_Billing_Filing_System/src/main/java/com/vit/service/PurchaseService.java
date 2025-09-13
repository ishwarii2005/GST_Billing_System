package com.vit.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vit.entity.Product;
import com.vit.entity.Purchase;
import com.vit.entity.PurchaseItem;
import com.vit.exception.ResourceNotFoundException;
import com.vit.repository.PurchaseRepository;

@Service
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final ProductService productService;

    public PurchaseService(PurchaseRepository purchaseRepository, ProductService productService) {
        this.purchaseRepository = purchaseRepository;
        this.productService = productService;
    }

    public Purchase save(Purchase purchase) {
        BigDecimal totalValue = BigDecimal.ZERO;
        BigDecimal totalTax = BigDecimal.ZERO;

        for (PurchaseItem item : purchase.getItems()) {
            item.setPurchase(purchase);

            // Calculate values
            BigDecimal itemValue = item.getPrice().multiply(BigDecimal.valueOf(item.getQty()));
            BigDecimal itemTax = itemValue.multiply(item.getTaxRate()).divide(BigDecimal.valueOf(100));

            item.setTaxableValue(itemValue);
            item.setTaxAmount(itemTax);
            item.setLineTotal(itemValue.add(itemTax));

            totalValue = totalValue.add(itemValue);
            totalTax = totalTax.add(itemTax);

            // Update stock
            Product product = productService.getProductById(item.getProductId());
            productService.updateStock(product.getId(), item.getQty());
        }

        purchase.setTotalValue(totalValue.add(totalTax));
        purchase.setTotalTax(totalTax);

        return purchaseRepository.save(purchase);
    }

    public List<Purchase> getByUser(Long userId) {
        return purchaseRepository.findByUserId(userId);
    }
    
    public Purchase update(Long id, Purchase updatedPurchase) {
        Purchase existing = purchaseRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Purchase not found with id: " + id));

        // Adjust stock before updating
        for (PurchaseItem oldItem : existing.getItems()) {
            productService.updateStock(oldItem.getProductId(), -oldItem.getQty());
        }

        // Set new values
        existing.setSupplierId(updatedPurchase.getSupplierId());
        existing.setInvoiceNo(updatedPurchase.getInvoiceNo());
        existing.setDate(updatedPurchase.getDate());
        existing.setItems(updatedPurchase.getItems());

        // Recalculate totals and update stock
        BigDecimal totalValue = BigDecimal.ZERO;
        BigDecimal totalTax = BigDecimal.ZERO;
        for (PurchaseItem item : updatedPurchase.getItems()) {
            item.setPurchase(existing);
            BigDecimal itemValue = item.getPrice().multiply(BigDecimal.valueOf(item.getQty()));
            BigDecimal itemTax = itemValue.multiply(item.getTaxRate()).divide(BigDecimal.valueOf(100));
            item.setTaxableValue(itemValue);
            item.setTaxAmount(itemTax);
            item.setLineTotal(itemValue.add(itemTax));

            totalValue = totalValue.add(itemValue);
            totalTax = totalTax.add(itemTax);

            productService.updateStock(item.getProductId(), item.getQty());
        }

        existing.setTotalValue(totalValue.add(totalTax));
        existing.setTotalTax(totalTax);

        return purchaseRepository.save(existing);
    }

    public void delete(Long id) {
        Purchase purchase = purchaseRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Purchase not found with id: " + id));

        // Reduce stock before deleting
        for (PurchaseItem item : purchase.getItems()) {
            productService.updateStock(item.getProductId(), -item.getQty());
        }

        purchaseRepository.delete(purchase);
    }

}
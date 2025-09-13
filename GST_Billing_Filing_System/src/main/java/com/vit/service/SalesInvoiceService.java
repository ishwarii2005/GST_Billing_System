package com.vit.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vit.entity.Product;
import com.vit.entity.SalesInvoice;
import com.vit.entity.SalesItem;
import com.vit.exception.ResourceNotFoundException;
import com.vit.repository.SalesInvoiceRepository;

@Service
public class SalesInvoiceService {

    private final SalesInvoiceRepository salesInvoiceRepository;
    private final ProductService productService;

    public SalesInvoiceService(SalesInvoiceRepository salesInvoiceRepository, ProductService productService) {
        this.salesInvoiceRepository = salesInvoiceRepository;
        this.productService = productService;
    }

    public SalesInvoice save(SalesInvoice invoice) {
        BigDecimal totalValue = BigDecimal.ZERO;
        BigDecimal totalTax = BigDecimal.ZERO;

        for (SalesItem item : invoice.getItems()) {
            item.setInvoice(invoice);

            // Calculate totals
            BigDecimal itemValue = item.getPrice().multiply(BigDecimal.valueOf(item.getQty()));
            BigDecimal itemTax = itemValue.multiply(item.getTaxRate()).divide(BigDecimal.valueOf(100));

            item.setTaxableValue(itemValue);
            item.setTaxAmount(itemTax);
            item.setLineTotal(itemValue.add(itemTax));

            totalValue = totalValue.add(itemValue);
            totalTax = totalTax.add(itemTax);

            // Reduce stock
            Product product = productService.getProductById(item.getProductId());
            productService.updateStock(product.getId(), -item.getQty());
        }

        invoice.setTotalValue(totalValue.add(totalTax));
        invoice.setTotalTax(totalTax);

        return salesInvoiceRepository.save(invoice);
    }

    public List<SalesInvoice> getByUser(Long userId) {
        return salesInvoiceRepository.findByUserId(userId);
    }
    
    public SalesInvoice update(Long id, SalesInvoice updatedInvoice) {
        SalesInvoice existing = salesInvoiceRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with id: " + id));


        for (SalesItem oldItem : existing.getItems()) {
            productService.updateStock(oldItem.getProductId(), oldItem.getQty());
        }

        // Set new values
        existing.setCustomerId(updatedInvoice.getCustomerId());
        existing.setInvoiceNo(updatedInvoice.getInvoiceNo());
        existing.setDate(updatedInvoice.getDate());
        existing.setItems(updatedInvoice.getItems());

        BigDecimal totalValue = BigDecimal.ZERO;
        BigDecimal totalTax = BigDecimal.ZERO;
        for (SalesItem item : updatedInvoice.getItems()) {
            item.setInvoice(existing);
            BigDecimal itemValue = item.getPrice().multiply(BigDecimal.valueOf(item.getQty()));
            BigDecimal itemTax = itemValue.multiply(item.getTaxRate()).divide(BigDecimal.valueOf(100));
            item.setTaxableValue(itemValue);
            item.setTaxAmount(itemTax);
            item.setLineTotal(itemValue.add(itemTax));

            totalValue = totalValue.add(itemValue);
            totalTax = totalTax.add(itemTax);

            productService.updateStock(item.getProductId(), -item.getQty());
        }

        existing.setTotalValue(totalValue.add(totalTax));
        existing.setTotalTax(totalTax);

        return salesInvoiceRepository.save(existing);
    }

    public void delete(Long id) {
        SalesInvoice invoice = salesInvoiceRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with id: " + id));

        // Restore stock
        for (SalesItem item : invoice.getItems()) {
            productService.updateStock(item.getProductId(), item.getQty());
        }

        salesInvoiceRepository.delete(invoice);
    }

}
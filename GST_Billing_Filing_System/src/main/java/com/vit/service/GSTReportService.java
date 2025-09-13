package com.vit.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vit.entity.Purchase;
import com.vit.entity.SalesInvoice;
import com.vit.repository.PurchaseRepository;
import com.vit.repository.SalesInvoiceRepository;

@Service
public class GSTReportService {

    private final PurchaseRepository purchaseRepository;
    private final SalesInvoiceRepository salesInvoiceRepository;

    public GSTReportService(PurchaseRepository purchaseRepository,
                            SalesInvoiceRepository salesInvoiceRepository) {
        this.purchaseRepository = purchaseRepository;
        this.salesInvoiceRepository = salesInvoiceRepository;
    }

    public String generateReport(Long userId, LocalDate start, LocalDate end) {
        List<Purchase> purchases = purchaseRepository.findByUserIdAndDateBetween(userId, start, end);
        List<SalesInvoice> sales = salesInvoiceRepository.findByUserIdAndDateBetween(userId, start, end);

        // Use BigDecimal to avoid precision errors in GST calculation
        BigDecimal totalPurchaseGST = purchases.stream()
                .map(p -> p.getTotalTax() != null ? p.getTotalTax() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalSalesGST = sales.stream()
                .map(s -> s.getTotalTax() != null ? s.getTotalTax() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal netPayable = totalSalesGST.subtract(totalPurchaseGST);

        return "GST Report (" + start + " to " + end + ")\n"
                + "Total Purchases GST: " + totalPurchaseGST + "\n"
                + "Total Sales GST: " + totalSalesGST + "\n"
                + (netPayable.compareTo(BigDecimal.ZERO) >= 0
                    ? "Net GST Payable: " + netPayable
                    : "ITC Carry Forward: " + netPayable.abs());
    }
}
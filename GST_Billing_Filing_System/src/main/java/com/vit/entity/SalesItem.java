package com.vit.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "sales_items")
public class SalesItem {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id")
    private SalesInvoice invoice;

    private Long productId;
    private Integer qty;
    private BigDecimal price;
    private BigDecimal taxableValue;
    private BigDecimal taxRate;
    private BigDecimal taxAmount;
    private BigDecimal lineTotal;
    
	public SalesItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SalesItem(Long id, SalesInvoice invoice, Long productId, Integer qty, BigDecimal price,
			BigDecimal taxableValue, BigDecimal taxRate, BigDecimal taxAmount, BigDecimal lineTotal) {
		super();
		this.id = id;
		this.invoice = invoice;
		this.productId = productId;
		this.qty = qty;
		this.price = price;
		this.taxableValue = taxableValue;
		this.taxRate = taxRate;
		this.taxAmount = taxAmount;
		this.lineTotal = lineTotal;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public SalesInvoice getInvoice() {
		return invoice;
	}
	public void setInvoice(SalesInvoice invoice) {
		this.invoice = invoice;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getTaxableValue() {
		return taxableValue;
	}
	public void setTaxableValue(BigDecimal taxableValue) {
		this.taxableValue = taxableValue;
	}
	public BigDecimal getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}
	public BigDecimal getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}
	public BigDecimal getLineTotal() {
		return lineTotal;
	}
	public void setLineTotal(BigDecimal lineTotal) {
		this.lineTotal = lineTotal;
	}

}

package com.vit.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String name;
    private String hsn;
    private BigDecimal price;         // selling price per unit
    private BigDecimal purchasePrice; // purchase price per unit
    private BigDecimal taxRate;       // GST rate %
    private Integer stockQty;         // current stock
    
	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Product(Long id, Long userId, String name, String hsn, BigDecimal price, BigDecimal purchasePrice,
			BigDecimal taxRate, Integer stockQty) {
		super();
		this.id = id;
		this.userId = userId;
		this.name = name;
		this.hsn = hsn;
		this.price = price;
		this.purchasePrice = purchasePrice;
		this.taxRate = taxRate;
		this.stockQty = stockQty;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHsn() {
		return hsn;
	}
	public void setHsn(String hsn) {
		this.hsn = hsn;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getPurchasePrice() {
		return purchasePrice;
	}
	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	public BigDecimal getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}
	public Integer getStockQty() {
		return stockQty;
	}
	public void setStockQty(Integer stockQty) {
		this.stockQty = stockQty;
	}

}
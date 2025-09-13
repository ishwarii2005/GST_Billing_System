package com.vit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "customers_suppliers")
public class CustomerSupplier {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId; // linked to business owner

    private String name;
    private String gstin;
    private String address;
    private String state;
    private String contact;

    @Enumerated(EnumType.STRING)
    private Type type; // CUSTOMER or SUPPLIER

    public enum Type {
        CUSTOMER, SUPPLIER
    }

	public CustomerSupplier() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CustomerSupplier(Long id, Long userId, String name, String gstin, String address, String state,
			String contact, Type type) {
		super();
		this.id = id;
		this.userId = userId;
		this.name = name;
		this.gstin = gstin;
		this.address = address;
		this.state = state;
		this.contact = contact;
		this.type = type;
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

	public String getGstin() {
		return gstin;
	}

	public void setGstin(String gstin) {
		this.gstin = gstin;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	} 

}
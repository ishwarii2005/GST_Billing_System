package com.vit.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vit.entity.Product;
import com.vit.exception.ResourceNotFoundException;
import com.vit.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product createProduct(Product product) {
        return repository.save(product);
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public Product getProductById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        Product existing = getProductById(id);
        existing.setUserId(updatedProduct.getUserId());
        existing.setName(updatedProduct.getName());
        existing.setHsn(updatedProduct.getHsn());
        existing.setPrice(updatedProduct.getPrice());
        existing.setPurchasePrice(updatedProduct.getPurchasePrice());
        existing.setTaxRate(updatedProduct.getTaxRate());
        existing.setStockQty(updatedProduct.getStockQty());
        return repository.save(existing);
    }

    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        repository.delete(product);
    }

    public List<Product> getByUser(Long userId) {
        return repository.findByUserId(userId);
    }

    public void updateStock(Long productId, int quantityChange) {
        Product product = getProductById(productId);
        Integer currentStock = product.getStockQty() == null ? 0 : product.getStockQty();
        product.setStockQty(currentStock + quantityChange);
        repository.save(product);
    }
}
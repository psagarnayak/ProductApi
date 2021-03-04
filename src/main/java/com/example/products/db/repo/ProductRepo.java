package com.example.products.db.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.products.db.entity.Product;

public interface ProductRepo extends JpaRepository<Product, Long>{

}

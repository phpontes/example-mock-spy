package com.paulo.example_mock_spy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paulo.example_mock_spy.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}

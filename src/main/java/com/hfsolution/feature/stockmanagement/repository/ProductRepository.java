package com.hfsolution.feature.stockmanagement.repository;

import com.hfsolution.app.repository.IBaseRepository;
import com.hfsolution.feature.stockmanagement.entity.Product;

public interface ProductRepository extends IBaseRepository<Product,Long>{


    Product findByProductId(Long id);
    Product findByProductName(String name);
    void deleteByProductId(Long id);
    void deleteByProductName(String name);
    
} 

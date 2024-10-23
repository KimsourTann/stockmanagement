package com.hfsolution.feature.stockmanagement.service.product;

import org.springframework.stereotype.Service;

import com.hfsolution.feature.stockmanagement.dto.request.ProductRequest;

@Service
public interface ProductService {

    
    public Object getProduct(int page , int size);  
    public Object getProductByID(Long id);  
    public Object getProductByName(String name);
    public Object importProduct(ProductRequest productRequest);
    public Object deleteProductById(Long id);
    public Object deleteProductByName(String name);
    public Object updateProductById(Long id , ProductRequest productRequest);
    public Object updateProductByName(String name , ProductRequest productRequest);

    
    
} 

package com.hfsolution.feature.stockmanagement.service.stock;

import org.springframework.stereotype.Service;

import com.hfsolution.feature.stockmanagement.dto.request.StockRequest;

@Service
public interface StockService {

    
    public Object getStock(int page , int size);  
    public Object getStockByProductID(Long id);  
    public Object getStockByProductName(String name);
    public Object importStock(StockRequest productRequest);
    public Object deleteStockByProductId(Long id);
    public Object deleteStockByProductName(String name);
    public Object updateStockByProductId(Long id , StockRequest productRequest);
    public Object updateStockByProductName(String name , StockRequest productRequest);

    
    
} 

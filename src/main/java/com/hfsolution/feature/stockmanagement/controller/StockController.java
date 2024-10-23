package com.hfsolution.feature.stockmanagement.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.hfsolution.feature.stockmanagement.dto.request.StockRequest;
import com.hfsolution.feature.stockmanagement.service.stock.StockService;

import static com.hfsolution.app.constant.AppConstant.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/stock/v1")
public class StockController {

    @Autowired
    private StockService stockService;
    
    @Autowired
    private HttpServletRequest httpServletRequest;

    @GetMapping("/list")
    private Object listStock(@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "10") int size){
        httpServletRequest.setAttribute(REQ_INFO,"list all stock");
        return stockService.getStock(page,size);
    }

    @GetMapping("/list/id/{id}")
    private Object listStockById( @PathVariable long id){
        return stockService.getStockByProductID(id);
    }

    @GetMapping("/list/name/{name}")
    private Object listStockByName( @PathVariable String name){
        return stockService.getStockByProductName(name);
    }

    @PostMapping("/import")
    private Object importStock(@Valid @RequestBody StockRequest StockRequest){
        return stockService.importStock(StockRequest);
    }

    @DeleteMapping("/delete/id/{id}")
    private Object deleteStockById( @PathVariable long id){
        return stockService.deleteStockByProductId(id);
    }

    @DeleteMapping("/delete/name/{name}")
    private Object deleteStockByName( @PathVariable String name){
        return stockService.deleteStockByProductName(name);
    }

    @PostMapping("/update/id/{id}")
    private Object updateStockById(@PathVariable long id,@Valid @RequestBody StockRequest StockRequest){
        return stockService.updateStockByProductId(id,StockRequest);
    }

    @PostMapping("/update/name/{name}")
    private Object updateStockByName(@PathVariable String name,@Valid @RequestBody StockRequest StockRequest){
        return stockService.updateStockByProductName(name,StockRequest);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }

    
}

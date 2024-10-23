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
import com.hfsolution.feature.stockmanagement.dto.request.ProductRequest;
import com.hfsolution.feature.stockmanagement.service.product.ProductService;
import static com.hfsolution.app.constant.AppConstant.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/product/v1")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private HttpServletRequest httpServletRequest;

    @GetMapping("/list")
    private Object listProduct(@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "10") int size){
        httpServletRequest.setAttribute(REQ_INFO,"list all product");
        return productService.getProduct(page,size);
    }

    @GetMapping("/list/id/{id}")
    private Object listProductById( @PathVariable long id){
        return productService.getProductByID(id);
    }

    @GetMapping("/list/name/{name}")
    private Object listProductByName( @PathVariable String name){
        return productService.getProductByName(name);
    }

    @PostMapping("/import")
    private Object importProduct(@Valid @RequestBody ProductRequest productRequest){
        return productService.importProduct(productRequest);
    }

    @DeleteMapping("/delete/id/{id}")
    private Object deleteProductById( @PathVariable long id){
        return productService.deleteProductById(id);
    }

    @DeleteMapping("/delete/name/{name}")
    private Object deleteProductByName( @PathVariable String name){
        return productService.deleteProductByName(name);
    }

    @PostMapping("/update/id/{id}")
    private Object updateProductById(@PathVariable long id,@Valid @RequestBody ProductRequest productRequest){
        return productService.updateProductById(id,productRequest);
    }

    @PostMapping("/update/name/{name}")
    private Object updateProductByName(@PathVariable String name,@Valid @RequestBody ProductRequest productRequest){
        return productService.updateProductByName(name,productRequest);
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

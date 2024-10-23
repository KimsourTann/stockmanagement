package com.hfsolution.feature.stockmanagement.service.product;

import static com.hfsolution.app.constant.AppResponseCode.FAIL_CODE;
import static com.hfsolution.app.constant.AppResponseCode.SUCCESS_CODE;
import static com.hfsolution.app.constant.AppResponseStatus.SUCCESS;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.hfsolution.app.dto.BaseEntityResponseDto;
import com.hfsolution.app.exception.AppException;
import com.hfsolution.app.exception.DatabaseException;
import com.hfsolution.app.util.AppTools;
import com.hfsolution.feature.stockmanagement.dao.ProductDao;
import com.hfsolution.feature.stockmanagement.dto.request.ProductRequest;
import com.hfsolution.feature.stockmanagement.dto.response.StockManagementResponse;
import com.hfsolution.feature.stockmanagement.entity.Product;
import jakarta.servlet.http.HttpServletRequest;
import static com.hfsolution.app.constant.AppConstant.*;


@Service
public class ProductServicelmp implements ProductService {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    public Object getProduct(int page , int size) {

        httpServletRequest.setAttribute(ACTION,"LIST ALL PRODUCT");
        StockManagementResponse response = new StockManagementResponse();
        
        try {
    
            BaseEntityResponseDto<Product> productResult = productDao.findAll(PageRequest.of(page-1, 
            size,Sort.by("productId").ascending()));
            response.setStatus(SUCCESS);
            response.setCode(SUCCESS_CODE);
            response.setData(productResult.getEntityList());
            return response;

        }catch (DatabaseException e) {
            throw e;   
        }catch (AppException e) {
            throw e;   
        }catch(Exception e){
            throw new AppException(FAIL_CODE,e.getMessage(),true);
        }
        
    }

    @Override
    public Object getProductByID(Long id) {

        httpServletRequest.setAttribute(ACTION,"LIST PRODUCT BY ID");
        StockManagementResponse response = new StockManagementResponse();
        try {
    
            BaseEntityResponseDto<Product> productResult = productDao.findByProductID(id);
            if(!productResult.getStatus().equals(SUCCESS) || productResult.getEntity()==null){
                String msg = AppTools.appGetMessage("006");
                throw new AppException("006",msg);
            }
            response.setStatus(SUCCESS);
            response.setCode(SUCCESS_CODE);
            response.setData(productResult.getEntity());
            return response;

        }catch (DatabaseException e) {
            throw e;   
        }catch (AppException e) {
            throw e;   
        }catch(Exception e){
            throw new AppException(FAIL_CODE,e.getMessage(),true);
        }
        
    }

    @Override
    public Object getProductByName(String name) {

        httpServletRequest.setAttribute(ACTION,"LIST PRODUCT BY NAME");
        StockManagementResponse response = new StockManagementResponse();
        try {
    
            BaseEntityResponseDto<Product> productResult = productDao.findByProductName(name);
            if(!productResult.getStatus().equals(SUCCESS) || productResult.getEntity()==null){
                String msg = AppTools.appGetMessage("006");
                throw new AppException("006",msg);
            }
            response.setStatus(SUCCESS);
            response.setCode(SUCCESS_CODE);
            response.setData(productResult.getEntity());
            return response;

        }catch (DatabaseException e) {
            throw e;   
        }catch (AppException e) {
            throw e;   
        }catch(Exception e){
            throw new AppException(FAIL_CODE,e.getMessage(),true);
        }
        
    }

    @Override
    public Object importProduct(ProductRequest productRequest) {

        httpServletRequest.setAttribute(ACTION,"IMPORT PRODUCT");
        StockManagementResponse response = new StockManagementResponse();
        try {

            Product product = new Product();
            product.setProductName(productRequest.getProductName());
            product.setProductDesc(productRequest.getProductDesc());
            product.setPrice(productRequest.getPrice());
            product.setCreatedDate(new Timestamp(System.currentTimeMillis()));
            product.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
            product.setExpiryDate(Timestamp.valueOf(LocalDateTime.of(LocalDate.parse(productRequest.getExpiryDate()), LocalTime.MIDNIGHT)));
            // BaseEntityResponseDto<Product> productResult = productDao.saveEntity(product);
            // if(!productResult.getStatus().equals(SUCCESS) || productResult.getEntity()==null){
            //     String msg = AppTools.appGetMessage("006");
            //     throw new AppException("006",msg);
            // }
            productDao.saveEntity(product);
            response.setStatus(SUCCESS);
            response.setCode(SUCCESS_CODE);
            response.setMsg(AppTools.appGetMessage("008"));
            return response;

        }catch (DatabaseException e) {
            throw e;   
        }catch (AppException e) {
            throw e;   
        }catch(Exception e){
            throw new AppException(FAIL_CODE,e.getMessage(),true);
        }
    }

    @Override
    public Object deleteProductById(Long id) {

        httpServletRequest.setAttribute(ACTION,"DELETE PRODUCT BY ID");
        StockManagementResponse response = new StockManagementResponse();
        try {
    
            productDao.deleteByProductID(id);
            String msg = AppTools.appGetMessage("007");
            response.setStatus(SUCCESS);
            response.setCode(SUCCESS_CODE);
            response.setMsg(msg);
            return response;

        }catch (DatabaseException e) {
            throw e;   
        }catch (AppException e) {
            throw e;   
        }catch(Exception e){
            throw new AppException(FAIL_CODE,e.getMessage(),true);
        }

    }

    @Override
    public Object deleteProductByName(String name) {

        httpServletRequest.setAttribute(ACTION,"DELETE PRODUCT BY NAME");
        StockManagementResponse response = new StockManagementResponse();
        try {
    
            productDao.deleteByProductName(name);
            String msg = AppTools.appGetMessage("007");
            response.setStatus(SUCCESS);
            response.setCode(SUCCESS_CODE);
            response.setMsg(msg);
            return response;

        }catch (DatabaseException e) {
            throw e;   
        }catch (AppException e) {
            throw e;   
        }catch(Exception e){
            throw new AppException(FAIL_CODE,e.getMessage(),true);
        }

    }

    @Override
    public Object updateProductById(Long id, ProductRequest productRequest) {

        httpServletRequest.setAttribute(ACTION,"UPDATE PRODUCT BY ID");
        StockManagementResponse response = new StockManagementResponse();
        try {

            //check product id
            BaseEntityResponseDto<Product> productResult = productDao.findByProductID(id);
            if(!productResult.getStatus().equals(SUCCESS) || productResult.getEntity()==null){
                String msg = AppTools.appGetMessage("006");
                throw new AppException("006",msg);
            }

            //updated
            Product existingProduct = productResult.getEntity();
            Optional.ofNullable(productRequest.getProductName()).ifPresent(existingProduct::setProductName);
            Optional.ofNullable(productRequest.getProductDesc()).ifPresent(existingProduct::setProductDesc);
            Optional.ofNullable(productRequest.getPrice()).ifPresent(existingProduct::setPrice);
            Optional.ofNullable(Timestamp.valueOf(LocalDateTime.of(LocalDate.parse(productRequest.getExpiryDate()), LocalTime.MIDNIGHT)))
            .ifPresent(existingProduct::setExpiryDate);
            existingProduct.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
            productDao.saveEntity(existingProduct);

            response.setStatus(SUCCESS);
            response.setCode(SUCCESS_CODE);
            response.setMsg(AppTools.appGetMessage("009"));
            return response;

        }catch (DatabaseException e) {
            throw e;   
        }catch (AppException e) {
            throw e;   
        }catch(Exception e){
            throw new AppException(FAIL_CODE,e.getMessage(),true);
        }

    }

    @Override
    public Object updateProductByName(String name, ProductRequest productRequest) {

        httpServletRequest.setAttribute(ACTION,"UPDATE PRODUCT BY ID");
        StockManagementResponse response = new StockManagementResponse();
        try {

            //check product id
            BaseEntityResponseDto<Product> productResult = productDao.findByProductName(name);
            if(!productResult.getStatus().equals(SUCCESS) || productResult.getEntity()==null){
                String msg = AppTools.appGetMessage("006");
                throw new AppException("006",msg);
            }

            //updated
            Product existingProduct = productResult.getEntity();
            Optional.ofNullable(productRequest.getProductName()).ifPresent(existingProduct::setProductName);
            Optional.ofNullable(productRequest.getProductDesc()).ifPresent(existingProduct::setProductDesc);
            Optional.ofNullable(productRequest.getPrice()).ifPresent(existingProduct::setPrice);
            Optional.ofNullable(Timestamp.valueOf(LocalDateTime.of(LocalDate.parse(productRequest.getExpiryDate()), LocalTime.MIDNIGHT)))
            .ifPresent(existingProduct::setExpiryDate);
            existingProduct.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
            productDao.saveEntity(existingProduct);
            
            response.setStatus(SUCCESS);
            response.setCode(SUCCESS_CODE);
            response.setMsg(AppTools.appGetMessage("009"));
            return response;

        }catch (DatabaseException e) {
            throw e;   
        }catch (AppException e) {
            throw e;   
        }catch(Exception e){
            throw new AppException(FAIL_CODE,e.getMessage(),true);
        }

    }

    


   
    
}

package com.hfsolution.feature.stockmanagement.service.stock;

import static com.hfsolution.app.constant.AppResponseCode.FAIL_CODE;
import static com.hfsolution.app.constant.AppResponseCode.SUCCESS_CODE;
import static com.hfsolution.app.constant.AppResponseStatus.SUCCESS;
import java.sql.Timestamp;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.hfsolution.app.dto.BaseEntityResponseDto;
import com.hfsolution.app.exception.AppException;
import com.hfsolution.app.exception.DatabaseException;
import com.hfsolution.app.util.AppTools;
import com.hfsolution.feature.stockmanagement.dao.StockDao;
import com.hfsolution.feature.stockmanagement.dto.request.StockRequest;
import com.hfsolution.feature.stockmanagement.dto.response.StockManagementResponse;
import com.hfsolution.feature.stockmanagement.entity.Stock;
import jakarta.servlet.http.HttpServletRequest;
import static com.hfsolution.app.constant.AppConstant.*;


@Service
public class StockServicelmp implements StockService {

    @Autowired
    private StockDao stockDao;
    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    public Object getStock(int page , int size) {

        httpServletRequest.setAttribute(ACTION,"LIST ALL STOCK");
        StockManagementResponse response = new StockManagementResponse();
        
        try {
    
            BaseEntityResponseDto<Stock> stockResult = stockDao.findAll(PageRequest.of(page-1, 
            size,Sort.by("stockId").ascending()));
            response.setStatus(SUCCESS);
            response.setCode(SUCCESS_CODE);
            response.setData(stockResult.getEntityList());
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
    public Object getStockByProductID(Long id) {

        httpServletRequest.setAttribute(ACTION,"LIST STOCK BY PRODUCT ID");
        StockManagementResponse response = new StockManagementResponse();
        try {
    
            BaseEntityResponseDto<Stock> productResult = stockDao.findStockByProductID(id);
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
    public Object getStockByProductName(String name) {

        httpServletRequest.setAttribute(ACTION,"LIST STOCK BY PRODUCT NAME");
        StockManagementResponse response = new StockManagementResponse();
        try {
    
            BaseEntityResponseDto<Stock> productResult = stockDao.findStockByProductName(name);
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
    public Object importStock(StockRequest stockRequest) {

        httpServletRequest.setAttribute(ACTION,"IMPORT STOCK");
        StockManagementResponse response = new StockManagementResponse();
        try {

            Stock stock ;

            //check product id
            BaseEntityResponseDto<Stock> stockResult = stockDao.findStockByProductID(stockRequest.getProductId());
            if(stockResult.getEntity()!=null){
                stock = stockResult.getEntity();
                stock.setProductId(stockRequest.getProductId());
                stock.setQty(stock.getQty()+stockRequest.getQty());
                stock.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
            }else{
                stock = new Stock();
                stock.setProductId(stockRequest.getProductId());
                stock.setQty(stockRequest.getQty());
                stock.setCreatedDate(new Timestamp(System.currentTimeMillis()));
                stock.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
            }
            stockDao.saveEntity(stock);
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
    public Object deleteStockByProductId(Long id) {

        httpServletRequest.setAttribute(ACTION,"DELETE STOCK BY PRODUCT ID");
        StockManagementResponse response = new StockManagementResponse();
        try {
    
            stockDao.deleteStockByProductID(id);
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
    public Object deleteStockByProductName(String name) {

        httpServletRequest.setAttribute(ACTION,"DELETE PRODUCT BY NAME");
        StockManagementResponse response = new StockManagementResponse();
        try {
    
            stockDao.deleteStockByProductName(name);
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
    public Object updateStockByProductId(Long id, StockRequest stockRequest) {

        httpServletRequest.setAttribute(ACTION,"UPDATE STOCK BY PRODUCT ID");
        StockManagementResponse response = new StockManagementResponse();
        try {

            //check source productid
            BaseEntityResponseDto<Stock> existingProductResult = stockDao.findStockByProductID(id);
            if(!existingProductResult.getStatus().equals(SUCCESS) || existingProductResult.getEntity()==null){
                String msg = AppTools.appGetMessage("006");
                throw new AppException("006",msg);
            }
            Stock stock ;

            //when target productid already exist in table (accumulate)
            BaseEntityResponseDto<Stock> targetProductResult = stockDao.findStockByProductID(stockRequest.getProductId());
            if(targetProductResult.getEntity()!=null){ 
                stock = targetProductResult.getEntity();
                stock.setQty(existingProductResult.getEntity().getQty()+targetProductResult.getEntity().getQty());

                //delete 

            //when target productid not yet exist in table (update to new) 
            }else{ 
                stock = existingProductResult.getEntity();
                Optional.ofNullable(stockRequest.getProductId()).ifPresent(stock::setProductId);
                Optional.ofNullable(stockRequest.getQty()).ifPresent(stock::setQty);
            }
            stock.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
            stockDao.saveEntity(stock);
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
    public Object updateStockByProductName(String name, StockRequest stockRequest) {

        httpServletRequest.setAttribute(ACTION,"UPDATE PRODUCT BY NAME");
        StockManagementResponse response = new StockManagementResponse();
        try {

            //check source productid
            BaseEntityResponseDto<Stock> existingProductResult = stockDao.findStockByProductName(name);
            if(!existingProductResult.getStatus().equals(SUCCESS) || existingProductResult.getEntity()==null){
                String msg = AppTools.appGetMessage("006");
                throw new AppException("006",msg);
            }
            Stock stock ;

            //when target productid already exist in table (accumulate)
            BaseEntityResponseDto<Stock> targetProductResult = stockDao.findStockByProductID(stockRequest.getProductId());
            if(targetProductResult.getEntity()!=null){ 
                stock = targetProductResult.getEntity();
                stock.setQty(existingProductResult.getEntity().getQty()+targetProductResult.getEntity().getQty());

                //delete 

            //when target productid not yet exist in table (update to new) 
            }else{ 
                stock = existingProductResult.getEntity();
                Optional.ofNullable(stockRequest.getProductId()).ifPresent(stock::setProductId);
                Optional.ofNullable(stockRequest.getQty()).ifPresent(stock::setQty);
            }
            stock.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
            stockDao.saveEntity(stock);
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

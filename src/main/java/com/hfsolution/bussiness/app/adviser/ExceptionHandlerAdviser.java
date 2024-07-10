package com.hfsolution.bussiness.app.adviser;


import static com.hfsolution.bussiness.app.constant.AppResponseCode.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.hfsolution.bussiness.app.dto.ExceptionResponse;
import com.hfsolution.bussiness.app.exception.AppException;
import com.hfsolution.bussiness.app.exception.DatabaseException;
import com.hfsolution.bussiness.app.exception.JwtException;
import com.hfsolution.bussiness.app.util.AppTools;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;

@RestControllerAdvice
public class ExceptionHandlerAdviser extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {
            SignatureException.class,
            Exception.class,
            AppException.class,
            DatabaseException.class,
            AccessDeniedException.class,
            JwtException.class,
            BadCredentialsException.class,
            ExpiredJwtException.class
    })
    protected ResponseEntity<ExceptionResponse> handleException(RuntimeException ex, WebRequest request) throws JsonProcessingException {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        HttpStatus status;
        // Determine the status code based on the exception type
        if (ex instanceof AppException) {
            AppException appException = (AppException) ex;
            String msg = appException.getMsg().isEmpty() ? AppTools.appGetMessage(appException.getCode()) : appException.getMsg();
            exceptionResponse.setErrorCode(appException.getCode());
            exceptionResponse.setMsg(msg);
            status = HttpStatus.OK;
        } else if (ex instanceof DatabaseException) {
            AppException appException = (AppException) ex;
            String msg = appException.getMsg().isEmpty()  ? AppTools.appGetMessage(appException.getCode()) : appException.getMsg();
            exceptionResponse.setErrorCode(appException.getCode());
            exceptionResponse.setMsg(msg);
            status = HttpStatus.OK;
        } else if (ex instanceof BadCredentialsException) {
            exceptionResponse.setErrorCode(UNAUTH);
            exceptionResponse.setMsg(AppTools.appGetMessage(UNAUTH));
            status = HttpStatus.UNAUTHORIZED;
        } else if (ex instanceof AccessDeniedException) {
            exceptionResponse.setErrorCode(FORBID);
            exceptionResponse.setMsg(AppTools.appGetMessage(FORBID));
            status = HttpStatus.FORBIDDEN;
        } 
        else if (ex instanceof SignatureException) {
            exceptionResponse.setErrorCode(FORBID);
            exceptionResponse.setMsg(ex.getMessage());
            status = HttpStatus.FORBIDDEN;
        } 
        else if (ex instanceof ExpiredJwtException) {
            exceptionResponse.setErrorCode(FORBID);
            exceptionResponse.setMsg(ex.getMessage());
            status = HttpStatus.FORBIDDEN;
        } 
        else {
            exceptionResponse.setErrorCode(INTERNAL_SERVER_ERROR);
            exceptionResponse.setMsg(ex.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(status).body(exceptionResponse);
    }

   
}

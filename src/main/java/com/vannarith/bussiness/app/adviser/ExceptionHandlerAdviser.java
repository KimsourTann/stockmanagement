package com.vannarith.bussiness.app.adviser;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException.Forbidden;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vannarith.bussiness.app.dto.ExceptionResponse;
import com.vannarith.bussiness.app.exception.AppException;
import com.vannarith.bussiness.app.exception.DatabaseException;
import com.vannarith.bussiness.app.exception.JwtException;
import com.vannarith.bussiness.app.util.AppTools;

import lombok.RequiredArgsConstructor;

import static com.vannarith.bussiness.app.constant.AppResponseStatus.FAIL;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandlerAdviser  extends ResponseEntityExceptionHandler{

    final ObjectMapper objectMapper;

    @ExceptionHandler(value = {Exception.class, AppException.class, DatabaseException.class, AccessDeniedException.class,JwtException.class,BadCredentialsException.class})
    protected ResponseEntity<ExceptionResponse> handleException (RuntimeException ex, WebRequest request) throws JsonProcessingException{
        // String bodyOfResponse = "An error occurred: " + ex.getMessage();
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        // Determine the status code based on the exception type
        if (ex instanceof AppException) {
            AppException appException = (AppException) ex;
            String msg=appException.getMsg()==null?AppTools.appGetMessage(appException.getCode()):appException.getMsg();
            exceptionResponse.setErrorCode(appException.getCode());
            exceptionResponse.setMsg(msg);
            exceptionResponse.setStatus(FAIL);
        } else if (ex instanceof DatabaseException) {
            AppException appException = (AppException) ex;
            String msg=appException.getMsg()==null?AppTools.appGetMessage(appException.getCode()):appException.getMsg();
            exceptionResponse.setErrorCode(appException.getCode());
            exceptionResponse.setMsg(msg);
            exceptionResponse.setStatus(FAIL);
        }else if (ex instanceof BadCredentialsException){
            exceptionResponse.setErrorCode("403");
            exceptionResponse.setMsg("Incorrect Email and Passowrd");
            exceptionResponse.setStatus(FAIL);
            return ResponseEntity.badRequest().body(exceptionResponse);
        } 
        else {
            return ResponseEntity.internalServerError().body(exceptionResponse);
        }

        // return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), status, request);



        return ResponseEntity.ok().body(exceptionResponse);
    }
    
}

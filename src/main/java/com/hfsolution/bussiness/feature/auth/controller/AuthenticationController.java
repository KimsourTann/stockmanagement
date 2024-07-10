package com.hfsolution.bussiness.feature.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static com.hfsolution.bussiness.app.constant.AppResponseCode.SUCCESS_CODE;
import static com.hfsolution.bussiness.app.constant.AppResponseStatus.SUCCESS;
import com.hfsolution.bussiness.app.dto.SuccessResponse;
import com.hfsolution.bussiness.feature.auth.dto.AuthenticationRequest;
import com.hfsolution.bussiness.feature.auth.dto.AuthenticationResponse;
import com.hfsolution.bussiness.feature.auth.services.AuthenticationService;
import com.hfsolution.bussiness.feature.user.entity.User;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;

  @PostMapping("/authenticate")
  public ResponseEntity<?> authenticate(
      @RequestBody AuthenticationRequest request
  ) {

    SuccessResponse<AuthenticationResponse> successResponse =  new SuccessResponse<>();
    successResponse.setData(service.authenticate(request));
    successResponse.setErrorCode(SUCCESS_CODE);
    successResponse.setMsg(SUCCESS);
    return ResponseEntity.ok(successResponse);
    
  }

  @PostMapping("/refresh-token")
  public void refreshToken(
      HttpServletRequest request,
      HttpServletResponse response
  ) throws IOException {
    service.refreshToken(request, response);
  }
}

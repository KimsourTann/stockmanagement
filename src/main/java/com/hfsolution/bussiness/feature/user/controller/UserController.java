package com.hfsolution.bussiness.feature.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hfsolution.bussiness.app.dto.SuccessResponse;
import com.hfsolution.bussiness.feature.user.dto.ChangePasswordRequest;
import com.hfsolution.bussiness.feature.user.entity.User;
import com.hfsolution.bussiness.feature.user.service.UserService;

import static com.hfsolution.bussiness.app.constant.AppResponseCode.SUCCESS_CODE;
import static com.hfsolution.bussiness.app.constant.AppResponseStatus.SUCCESS;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(
          @RequestBody ChangePasswordRequest request,
          Principal connectedUser
    ) {
        service.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getInfo")
    public ResponseEntity<?> getInfo(
          Principal connectedUser
    ) {
        SuccessResponse<User> successResponse =  new SuccessResponse<>();
        successResponse.setData(service.getInfo(connectedUser));
        successResponse.setErrorCode(SUCCESS_CODE);
        successResponse.setMsg(SUCCESS);
        return ResponseEntity.ok(successResponse);
    }
}
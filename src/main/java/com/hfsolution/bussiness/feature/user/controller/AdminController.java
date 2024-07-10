package com.hfsolution.bussiness.feature.user.controller;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hfsolution.bussiness.feature.auth.dto.AuthenticationResponse;
import com.hfsolution.bussiness.feature.auth.dto.RegisterRequest;
import com.hfsolution.bussiness.feature.auth.services.AuthenticationService;
import com.hfsolution.bussiness.feature.user.dto.ChangeRoleRequest;
import com.hfsolution.bussiness.feature.user.dto.DeleteUserRequest;
import com.hfsolution.bussiness.feature.user.dto.ResetPasswordRequest;
import com.hfsolution.bussiness.feature.user.service.UserService;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
// @PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AuthenticationService authService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
        @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/change-role")
    public ResponseEntity<?> changeRole(
          @RequestBody ChangeRoleRequest request,
          Principal connectedUser
    ) {
        userService.changeRole(request, connectedUser);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/delete-user")
    public ResponseEntity<?> deleteUser(
          @RequestBody DeleteUserRequest request
    ) {
        userService.deleteUser(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
          @RequestBody ResetPasswordRequest request
    ) {
        userService.resetPassword(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    // @PreAuthorize("hasAuthority('admin:read')")
    public String get() {
        return "GET:: admin controller";
    }

    @PostMapping
    // @PreAuthorize("hasAuthority('admin:create')")
    @Hidden
    public String post() {
        return "POST:: admin controller";
    }
    @PutMapping
    // @PreAuthorize("hasAuthority('admin:update')")
    @Hidden
    public String put() {
        return "PUT:: admin controller";
    }
    @DeleteMapping
    // @PreAuthorize("hasAuthority('admin:delete')")
    @Hidden
    public String delete() {
        return "DELETE:: admin controller";
    }
}
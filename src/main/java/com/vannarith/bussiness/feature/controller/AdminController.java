package com.vannarith.bussiness.feature.controller;

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

import com.vannarith.bussiness.feature.auth.AuthenticationResponse;
import com.vannarith.bussiness.feature.auth.AuthenticationService;
import com.vannarith.bussiness.feature.auth.RegisterRequest;
import com.vannarith.bussiness.feature.user.ChangeRoleRequest;
import com.vannarith.bussiness.feature.user.DeleteUserRequest;
import com.vannarith.bussiness.feature.user.ResetPasswordRequest;
import com.vannarith.bussiness.feature.user.UserService;

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

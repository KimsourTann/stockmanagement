package com.hfsolution.bussiness.feature.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hfsolution.bussiness.app.dto.SearchRequestDTO;
import com.hfsolution.bussiness.app.dto.SuccessResponse;
import com.hfsolution.bussiness.feature.user.entity.User;
import com.hfsolution.bussiness.feature.user.service.UserService;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import static com.hfsolution.bussiness.app.constant.AppResponseCode.SUCCESS_CODE;
import static com.hfsolution.bussiness.app.constant.AppResponseStatus.SUCCESS;

@RestController
@RequestMapping("/api/v1/management")
@RequiredArgsConstructor
@Tag(name = "Management")
public class ManagementController {


    final UserService userService;

    @Operation(
            description = "Get endpoint for manager",
            summary = "This is a summary for management get endpoint",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )
            }

    )

    @PostMapping("/users")
    public ResponseEntity<?> getUsersInfo(@RequestBody SearchRequestDTO request) {
        
        SuccessResponse<List<User>> successResponse =  new SuccessResponse<>();
        successResponse.setData(userService.getUsersInfo(request));
        successResponse.setErrorCode(SUCCESS_CODE);
        successResponse.setMsg(SUCCESS);
        return ResponseEntity.ok(successResponse);
    }

    

    @GetMapping
    public String get() {
        return "GET:: management controller";
    }
    
    @PutMapping
    public String put() {
        return "PUT:: management controller";
    }
    @DeleteMapping
    public String delete() {
        return "DELETE:: management controller";
    }
}
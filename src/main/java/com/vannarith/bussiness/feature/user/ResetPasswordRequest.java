package com.vannarith.bussiness.feature.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResetPasswordRequest {
    private String email;
    private String newPassword;
}
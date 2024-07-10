package com.hfsolution.bussiness.feature.user.dto;

import com.hfsolution.bussiness.feature.user.Enum.Role;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangeRoleRequest {
    @Enumerated(EnumType.STRING)
    private Role newRole;
}

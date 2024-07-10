package com.hfsolution.bussiness.app.dto;

import static com.hfsolution.bussiness.app.constant.AppResponseStatus.SUCCESS;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SuccessResponse<T> extends BaseResponse{
    T data;
    @Override
    public String getStatus() {
        return SUCCESS;
    }
}
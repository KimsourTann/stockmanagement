package com.hfsolution.bussiness.app.dto;

import static com.hfsolution.bussiness.app.constant.AppResponseStatus.FAIL;

import com.hfsolution.bussiness.app.util.AppTools;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseResponse {
    String errorCode;
    String msg;
    String status = FAIL;
    String responseTime = AppTools.getCurrentDateString();
}

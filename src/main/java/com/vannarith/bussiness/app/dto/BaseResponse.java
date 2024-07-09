package com.vannarith.bussiness.app.dto;

import com.vannarith.bussiness.app.util.AppTools;

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
    String status;
    String responseTime = AppTools.getCurrentDateString();
}

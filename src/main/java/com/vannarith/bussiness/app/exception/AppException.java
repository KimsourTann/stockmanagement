package com.vannarith.bussiness.app.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppException extends RuntimeException {
	
	private static final long serialVersionUID = 5531221281437904797L;
	private String code=""; // Error Code to app
	private String msg=""; // Message write log for developer

	public AppException(String code) {
		this.code = code;
	}
	public AppException(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}

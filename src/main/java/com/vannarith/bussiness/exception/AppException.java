package com.vannarith.bussiness.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppException extends RuntimeException {
	
	private static final long serialVersionUID = 5531221281437904797L;
	private String code=""; // Error Code to app
	private String msgDev=""; // Message write log for developer
	private String cmd=""; //
	private String ref="";

	public AppException(String code) {
		this.code = code;
	}

	public AppException(String code, String msgDev) {
		this.code = code;
		this.msgDev = msgDev;
	}

	public AppException(String code, String msgDev, String cmd) {
		this.code = code;
		this.msgDev = msgDev;
		this.cmd = cmd;
	}

	public AppException(String code, String msgDev, String cmd,String ref) {
		this.code = code;
		this.msgDev = msgDev;
		this.cmd = cmd;
		this.ref = ref;
	}
}

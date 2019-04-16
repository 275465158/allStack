package com.ecms.exceptions;

public class CommonBusinessException extends RuntimeException {

	public CommonBusinessException(String msg) {
		super(msg);
	}

	public CommonBusinessException(Throwable cause) {
		super(cause);
	}

	public CommonBusinessException(String msg, Throwable cause) {
		super(msg, cause);
	}
}

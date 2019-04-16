package com.ecms.utils;

import com.ecms.common.Result;

public class ResultUtils {

	public static final int CODE_SUCCESS = 1000;
	public static final int CODE_FAILURE = 1001;
	public static final int CODE_NOLOGIN = 1002;
	public static final String MSG_SUCCESS = "success";

	public static <T> Result<T> success() {
		Result<T> result = new Result<T>();
		result.setSuccess(true);
		result.setCode(CODE_SUCCESS);
		result.setMessage(MSG_SUCCESS);
		return result;
	}

	public static <T> Result<T> success(T data) {
		Result<T> result = new Result<T>();
		result.setSuccess(true);
		result.setCode(CODE_SUCCESS);
		result.setMessage(MSG_SUCCESS);
		result.setData(data);
		return result;
	}

	public static <T> Result<T> failure(String msg) {
		Result<T> result = new Result<T>();
		result.setSuccess(false);
		result.setCode(CODE_FAILURE);
		result.setMessage(msg);
		return result;
	}

	public static <T> Result<T> failure(Exception exception) {
		Result<T> result = new Result<T>();
		result.setSuccess(false);
		result.setCode(CODE_FAILURE);
		result.setMessage(exception.getMessage());
		return result;
	}
}

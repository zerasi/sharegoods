package com.so.demosboot.modules.sys.utils;

import java.io.Serializable;

public class Result implements Serializable{

	private boolean success;
	
	private Object message;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}

	public Result(boolean success, Object message) {
		super();
		this.success = success;
		this.message = message;
	}
	
}

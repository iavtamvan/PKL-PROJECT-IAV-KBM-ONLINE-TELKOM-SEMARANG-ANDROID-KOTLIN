package com.iavariav.kbmonline.model;

import com.google.gson.annotations.SerializedName;

public class ErrorModel{

	@SerializedName("error_msg")
	private String errorMsg;

	@SerializedName("error")
	private boolean error;

	public void setErrorMsg(String errorMsg){
		this.errorMsg = errorMsg;
	}

	public String getErrorMsg(){
		return errorMsg;
	}

	public void setError(boolean error){
		this.error = error;
	}

	public boolean isError(){
		return error;
	}
}
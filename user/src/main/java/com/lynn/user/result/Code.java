package com.lynn.user.result;

public enum Code {

	ERROR(0,"请求失败"),
	SUCCESS(1,"请求成功"),
	NO_DATA(2,"暂无数据"),
	EXP_PARAM(3,"参数错误"),
	NO_AUTH(4,"权限不足");

	private int status;
	
	private String message;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	private Code(int status,String message){
		this.status = status;
		this.message = message;
	}
	
}

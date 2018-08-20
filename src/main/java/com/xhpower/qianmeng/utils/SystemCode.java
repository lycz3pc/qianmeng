package com.xhpower.qianmeng.utils;

public enum SystemCode {

	VERIFICATION_FAILURE(10100, "后台验证失败"), FILE_UPLOAD_FAILURE(10200, "文件上传失败");

	private int code;
	// 成员变量
	private String msg;

	// 构造方法
	private SystemCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
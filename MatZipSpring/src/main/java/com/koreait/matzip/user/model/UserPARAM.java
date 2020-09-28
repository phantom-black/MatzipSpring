package com.koreait.matzip.user.model;

public class UserPARAM extends UserVO {
	private String msg;
	private int i_rest;
	private String proc_type;

	public String getProc_type() {
		return proc_type;
	}

	public void setProc_type(String proc_type) {
		this.proc_type = proc_type;
	}

	public int getI_rest() {
		return i_rest;
	}

	public void setI_rest(int i_rest) {
		this.i_rest = i_rest;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}

package com.koreait.matzip.rest.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class RestFile implements Serializable  {
	private static final long serialVersionUID = 1L;
	
	private int i_rest;
	private List<MultipartFile> menu_pic;
	
	
	public int getI_rest() {
		return i_rest;
	}
	public void setI_rest(int i_rest) {
		this.i_rest = i_rest;
	}
	public List<MultipartFile> getMenu_pic() {
		return menu_pic;
	}
	public void setMenu_pic(List<MultipartFile> menu_pic) {
		this.menu_pic = menu_pic;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

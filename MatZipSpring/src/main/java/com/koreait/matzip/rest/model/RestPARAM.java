package com.koreait.matzip.rest.model;

public class RestPARAM extends RestVO {
	private double sw_lat;
	private double sw_lng;
	private double ne_lat;
	private double ne_lng;
	private int seq;
	private String menu_pic;
	
	public String getMenu_pic() {
		return menu_pic;
	}
	public void setMenu_pic(String menu_pic) {
		this.menu_pic = menu_pic;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public double getSw_lat() {
		return sw_lat;
	}
	public void setSw_lat(double sw_lat) {
		this.sw_lat = sw_lat;
	}
	public double getSw_lng() {
		return sw_lng;
	}
	public void setSw_lng(double sw_lng) {
		this.sw_lng = sw_lng;
	}
	public double getNe_lat() {
		return ne_lat;
	}
	public void setNe_lat(double ne_lat) {
		this.ne_lat = ne_lat;
	}
	public double getNe_lng() {
		return ne_lng;
	}
	public void setNe_lng(double ne_lng) {
		this.ne_lng = ne_lng;
	}
}

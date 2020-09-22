package com.koreait.matzip.rest;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.koreait.matzip.rest.model.RestDMI;
import com.koreait.matzip.rest.model.RestPARAM;

@Mapper
public interface RestMapper { // interface라서 메소드들 자동으로 public 들어감
	int insRest(RestPARAM param);
	
	List<RestDMI> selRestList(RestPARAM param);
	
	RestDMI selRest(RestPARAM param);
}

package com.koreait.matzip.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.koreait.matzip.rest.model.RestDMI;
import com.koreait.matzip.rest.model.RestPARAM;
import com.koreait.matzip.rest.model.RestVO;

@Service
public class RestService {
	
	@Autowired
	private RestMapper mapper;
	
	String selRestList(RestPARAM param) {
		 List<RestDMI> list = mapper.selRestList(param);
		 Gson gson = new Gson();
		 return gson.toJson(list);
	}
	
	int insRest(RestVO param) {
		int result = mapper.intRest(param);
		return result;
	}
}

package com.koreait.matzip.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.koreait.matzip.Const;
import com.koreait.matzip.SecurityUtils;
import com.koreait.matzip.ViewRef;
import com.koreait.matzip.rest.model.RestPARAM;
import com.koreait.matzip.rest.model.RestVO;

@Controller
@RequestMapping("/rest")
public class RestController {
	
	@Autowired
	private RestService service;
	
	@RequestMapping("/map")
	public String restMap(Model model) {
		model.addAttribute(Const.TITLE, "지도보기");
		model.addAttribute(Const.VIEW, "rest/restMap");
		return ViewRef.TEMP_MENU_TEMP;
	}
	
	@RequestMapping("/ajaxGetList")
	@ResponseBody public String ajaxGetList(RestPARAM param) {
		System.out.println("sw_lat: " + param.getSw_lat());
		System.out.println("sw_lng: " + param.getSw_lng());
		System.out.println("ne_lat: " + param.getNe_lat());
		System.out.println("ne_lng: " + param.getNe_lng());
		
		return service.selRestList(param);
	}
	
	@RequestMapping("/reg")
	public String restReg(Model model) {
		model.addAttribute(Const.TITLE, "가게등록");
		model.addAttribute(Const.VIEW, "rest/restReg");
		return ViewRef.TEMP_DEFAULT;
	}
	
	@RequestMapping(value="/reg", method = RequestMethod.POST)
	public String restReg(RestVO param, HttpServletRequest request, RedirectAttributes ra) {
		int i_user = SecurityUtils.getLoginUserPk(request);
		param.setI_user(i_user);
		
		int result = service.insRest(param);
		
		if(result != 1) {
			ra.addFlashAttribute("data", param);
			return "redirect:/rest/reg"; // 에러쓰
		}
		
		return "redirect:/rest/map";
	}
}

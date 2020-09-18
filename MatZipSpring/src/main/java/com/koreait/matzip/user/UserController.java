package com.koreait.matzip.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.koreait.matzip.Const;
import com.koreait.matzip.ViewRef;
import com.koreait.matzip.user.model.UserDTO;
import com.koreait.matzip.user.model.UserVO;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	// 정석대로는 UserService도 Interface로 만든 후 상속받아서 작성한 친구를 데려와야 함
	private UserService service;
	
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String login(Model model, @RequestParam(required=false, defaultValue="0") int err) {
		
		if(err > 0) {
			switch(err) {
			case 2:
				model.addAttribute("msg", "아이디 없음");
				break;
			case 3:
				model.addAttribute("msg", "비밀번호 틀림");
				break;
			default:
				model.addAttribute("msg", "에러발생");
			}
		}

		model.addAttribute(Const.TITLE, "로그인");
		model.addAttribute(Const.VIEW, "user/login");
		return ViewRef.TEMP_DEFAULT;
	}
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public String login(UserDTO param) {
//		System.out.println("id: " + param.getUser_id());
//		System.out.println("pw: " + param.getUser_pw());
		
		int result = service.login(param);
		
		if(result == 1) {
			return "redirect:/rest/map";
		}
		return "redirect:/user/login?err=" + result;
	}
	
	@RequestMapping(value="/join", method = RequestMethod.GET)
	public String join(Model model, @RequestParam(required=false, defaultValue="0") int err) { // 딱 한 값만 받을 때는 굳이 헤비하게 VO로 받지 않고 @RequestParam 사용
												// @RequestParam(required=false, defaultValue="0") int err  <- int는 Integer와 달리 null값 받지 못하므로 defaultValue 넣어줘야 함
												// @RequestParam(required=false) Integer err
												// String값 대신 바로 int로 받을 수 있다
												// 보내고 있는 이름과 변수명 같다면 더 적을 필요 x
												// 이름이 다르다면 @RequestParam(value="err", required=false) Integer error와 같이 사용
												// required=true(true가 기본값임) <- key에 대한 value값이 항상 있어야 함(필수)
		
		System.out.println("err: " + err);
		
		if(err > 0) {
			model.addAttribute("msg", "에러가 발생하였습니다.");
		}
		model.addAttribute(Const.TITLE,  "회원가입");
		model.addAttribute(Const.VIEW,  "user/join");
		return ViewRef.TEMP_DEFAULT;
	}
	
	@RequestMapping(value="/join", method = RequestMethod.POST)
	public String join(UserVO param) {
		int result = service.join(param);
		
		if(result == 1) {
			return "redirect:/user/login";
		}
		
		return "redirect:/user/join?err=" + result;
	}
}
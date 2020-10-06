package com.koreait.matzip.user;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.koreait.matzip.Const;
import com.koreait.matzip.SecurityUtils;
import com.koreait.matzip.ViewRef;
import com.koreait.matzip.user.model.UserPARAM;
import com.koreait.matzip.user.model.UserVO;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	// 정석대로는 UserService도 Interface로 만든 후 상속받아서 작성한 친구를 데려와야 함
	private UserService service;
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logout(HttpSession hs) {
		hs.invalidate();
		return "redirect:/";
	}
	
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String login(Model model, @RequestParam(required=false, defaultValue="0") int err) {

		model.addAttribute(Const.TITLE, "로그인");
		model.addAttribute(Const.VIEW, "user/login");
		return ViewRef.TEMP_DEFAULT;
	}
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public String login(UserPARAM param, HttpSession hs, RedirectAttributes ra) {
//		System.out.println("id: " + param.getUser_id());
//		System.out.println("pw: " + param.getUser_pw());
		
		int result = service.login(param);
		
		if(result == Const.SUCCESS) {
			hs.setAttribute(Const.LOGIN_USER, param);
			return "redirect:/";
		}
		String msg = null;
		if(result == Const.NO_ID) {
			msg = "아이디를 확인해 주세요.";
		} else if(result == Const.NO_PW) {
			msg = "비밀번호를 확인해 주세요.";
		}
		
		param.setMsg(msg);
		ra.addFlashAttribute("data", param); // 객체로 넘어감 + 주솟값에 쿼리스트링으로 값이 박히지 않음
														// addFlashAttribute : session에 박고 쓰고 나면 알아서 세션에서 지움 == 마치 post처럼 쓸 수 있음
		return "redirect:/user/login";
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
	public String join(UserVO param, RedirectAttributes ra) {
		int result = service.join(param);
		
		if(result == 1) {
			return "redirect:/user/login";
		}
		
		ra.addAttribute("err", result); // 주솟값에 key값 value값 박아줌, 쿼리스트링
		return "redirect:/user/join";
	}
	
	@RequestMapping(value="/ajaxIdChk", method=RequestMethod.POST)
	@ResponseBody // 이거 주면 jsp파일 찾지 않음, 이거 자체가 응답, 결과물
	public String ajaxIdChk(@RequestBody UserPARAM param) {
		System.out.println("user_id: " + param.getUser_id());
		int result = service.login(param);
		return String.valueOf(result);
	}
	
	@RequestMapping(value="/ajaxToggleFavorite", method=RequestMethod.GET)
	@ResponseBody
	public int ajaxToggleFavorite(UserPARAM param, HttpSession hs) {
		System.out.println("==> ajaxToggleFavorite");
		int i_user = SecurityUtils.getLoginUserPk(hs);
		param.setI_user(i_user);
		return service.ajaxToggleFavorite(param);
	}
	
	@RequestMapping(value="/favorite")
	public String favorite(Model model, HttpSession hs) {
		int i_user = SecurityUtils.getLoginUserPk(hs);
		
		UserPARAM param = new UserPARAM();
		param.setI_user(i_user);
		
		model.addAttribute("data", service.selFavoriteList(param));
		
		model.addAttribute(Const.CSS, new String[] {"userFavorite"});
		model.addAttribute(Const.TITLE, "찜");
		model.addAttribute(Const.VIEW, "user/favorite");
		return ViewRef.TEMP_MENU_TEMP;
	}
}
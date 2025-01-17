package com.koreait.matzip.rest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.koreait.matzip.CommonUtils;
import com.koreait.matzip.Const;
import com.koreait.matzip.FileUtils;
import com.koreait.matzip.SecurityUtils;
import com.koreait.matzip.model.CodeVO;
import com.koreait.matzip.model.CommonMapper;
import com.koreait.matzip.rest.model.RestDMI;
import com.koreait.matzip.rest.model.RestFile;
import com.koreait.matzip.rest.model.RestPARAM;
import com.koreait.matzip.rest.model.RestRecMenuVO;

@Service
public class RestService {
	
	@Autowired
	private RestMapper mapper;
	
	@Autowired
	private CommonMapper cMapper;
	
	public int insRest(RestPARAM param) {
		return mapper.insRest(param);
	}
	
	public void updAddHits(RestPARAM param, HttpServletRequest req) {
		String myIp = req.getRemoteAddr();
		ServletContext ctx = req.getServletContext(); // 어플리케이션은 공용, pageContext, request, session 은 개인용
		
		int i_user = SecurityUtils.getLoginUserPk(req);
		
		String currentRestReadIp = (String)ctx.getAttribute(Const.CURRENT_REST_READ_IP + param.getI_rest());
		if(currentRestReadIp == null || !currentRestReadIp.equals(myIp)) {
			
			param.setI_user(i_user); // 내가 쓴 글이면 조회수 안 올라가게 쿼리문으로 막음
			// 조회수 올림 처리 예정
			mapper.updAddHits(param);
			ctx.setAttribute(Const.CURRENT_REST_READ_IP + param.getI_rest(), myIp);
		}
	}
	
	public int insRestMenu(RestFile param, int i_user) {
		if(_authFail(param.getI_rest(), i_user)) {
			return Const.FAIL;
		}
		System.out.println(Const.realPath);
		
		String path = Const.realPath + "/resources/img/rest/" + param.getI_rest() + "/menu/";
		
		List<RestRecMenuVO> list = new ArrayList();
		
		for(MultipartFile mf : param.getMenu_pic()) {
			RestRecMenuVO vo = new RestRecMenuVO();
			list.add(vo);
			
			String saveFileNm = FileUtils.saveFile(path, mf);
			vo.setMenu_pic(saveFileNm);
			vo.setI_rest(param.getI_rest());
		}
		
		for(RestRecMenuVO vo : list) {
			mapper.insRestMenu(vo);
		}
		
		return Const.SUCCESS;
	}
	
	public List<RestDMI> selRestList(RestPARAM param) {
		 return mapper.selRestList(param);
	}
	
	public List<RestRecMenuVO> selRestMenus(RestPARAM param) {
		return mapper.selRestMenus(param);
	}
	
	public List<CodeVO> selCategoryList()  {
		CodeVO p = new CodeVO();
		p.setI_m(1); // 음식점 카테고리 코드 = 1
		
		return cMapper.selCodeList(p);
	}
	
	public RestDMI selRest(RestPARAM param) {
		return mapper.selRest(param);
	}
	
	public List<RestRecMenuVO> selRestRecMenus(RestPARAM param) {
		return mapper.selRestRecMenus(param);
	}
	
	@Transactional // Transactional 있는데서 try catch문으로 감싸면 안되는 듯
	public void delRestTran(RestPARAM param) {
		mapper.delRestRecMenu(param);
		mapper.delRestMenu(param);
		mapper.delRest(param);
	}
	
	public int insRecMenus(MultipartHttpServletRequest mReq) {
		int i_user = SecurityUtils.getLoginUserPk(mReq.getSession());
		int i_rest = Integer.parseInt(mReq.getParameter("i_rest"));
		if(_authFail(i_rest, i_user)) {
			return Const.FAIL;
		}
		
		List<MultipartFile> fileList = mReq.getFiles("menu_pic");
		String[] menuNmArr = mReq.getParameterValues("menu_nm");
		String[] menuPriceArr = mReq.getParameterValues("menu_price");
		
		String path = Const.realPath + "/resources/img/rest/" + i_rest + "/rec_menu/";
		
		List<RestRecMenuVO> list = new ArrayList();
		
		for(int i=0; i<menuNmArr.length; i++) {
			RestRecMenuVO vo = new RestRecMenuVO();
			list.add(vo);
			
			String menu_nm = menuNmArr[i];
			int menu_price = CommonUtils.parseStrToInt(menuPriceArr[i]);
			vo.setI_rest(i_rest);
			vo.setMenu_nm(menu_nm);
			vo.setMenu_price(menu_price);
			
			// 각 파일 저장
			MultipartFile mf = fileList.get(i);
			String saveFileNm = FileUtils.saveFile(path, mf);
			vo.setMenu_pic(saveFileNm);
		}
		
		for(RestRecMenuVO vo : list) {
			mapper.insRestRecMenu(vo);
		}
		
		return i_rest;
	}
	
	public int delRestRecMenu(RestPARAM param, String realPath) {
		// 파일 삭제
		List<RestRecMenuVO> list = mapper.selRestRecMenus(param);
		if(list.size() == 1) {
			RestRecMenuVO item = list.get(0);
			
			if(item.getMenu_pic() != null && !"".equals(item.getMenu_pic())) { // 이미지 있음 -> 삭제!
				File file = new File(realPath + item.getMenu_pic());
				if(file.exists()) {
					if(file.delete()) { // 삭제 안될 경우 대비해 기록으로 남겨놔야, delete() <- boolean 값 넘어옴
						// delete 실행 성공 시
						return mapper.delRestRecMenu(param);
					} else { // delete 안 되었을 때는 삭제 실행 X
						return 0;
					}
				}
			}
		}
		
		return mapper.delRestRecMenu(param);
	}

	public int delRestMenu(RestPARAM param) {
		if(param.getMenu_pic() != null && !"".equals(param.getMenu_pic())) {
			String path = Const.realPath + "/resources/img/rest/" + param.getI_rest() + "/menu/";
			
			if(FileUtils.delFile(path + param.getMenu_pic())) {
				return mapper.delRestMenu(param);
			} else {
				return Const.FAIL;
			}
		}
		return mapper.delRestMenu(param);
	}
	
	private boolean _authFail(int i_rest, int i_user) { // 로그인한 사람의 i_user 값
		RestPARAM param = new RestPARAM();
		param.setI_rest(i_rest);
		
		int dbI_user = mapper.selRestChkUser(i_rest); // int selRestChkUser(int i_rest);
		if(i_user != dbI_user) {
			return true; // 인증실패
		}
		
		return false; // 인증성공
	}
}

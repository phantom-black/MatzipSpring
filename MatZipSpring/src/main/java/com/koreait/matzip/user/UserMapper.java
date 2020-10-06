package com.koreait.matzip.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.koreait.matzip.user.model.UserDMI;
import com.koreait.matzip.user.model.UserPARAM;
import com.koreait.matzip.user.model.UserVO;

@Mapper
public interface UserMapper {
	int insUser(UserVO param);
	int insFavorite(UserPARAM param);
	
	UserDMI selUser(UserPARAM param);
	List<UserDMI> selFavoriteList(UserPARAM param);
	
	int delFavorite(UserPARAM param);
}

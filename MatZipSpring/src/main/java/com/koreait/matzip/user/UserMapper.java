package com.koreait.matzip.user;

import org.apache.ibatis.annotations.Mapper;

import com.koreait.matzip.user.model.UserDMI;
import com.koreait.matzip.user.model.UserPARAM;
import com.koreait.matzip.user.model.UserVO;

@Mapper
public interface UserMapper {
	int insUser(UserVO p);
	int insFavorite(UserPARAM param);
	
	UserDMI selUser(UserPARAM p);
	
	int delFavorite(UserPARAM param);
}

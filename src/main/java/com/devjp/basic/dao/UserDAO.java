package com.devjp.basic.dao;

import com.devjp.basic.vo.UserVO;

public interface UserDAO {
	UserVO selectOneByEmail(String email);
}

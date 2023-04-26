package com.devjp.basic.daoImpl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.devjp.basic.dao.UserDAO;
import com.devjp.basic.vo.UserVO;

@Repository
public class UserDAOImpl implements UserDAO {
	
	@Autowired
	private SqlSession sqlsession;
	
	private static final String NS = "UserMapper.";

	@Override
	public UserVO selectOneByEmail(String email) {
		return sqlsession.selectOne(NS+"selectOneByEmail", email);
	}
}

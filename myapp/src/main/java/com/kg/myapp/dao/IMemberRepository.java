package com.kg.myapp.dao;

import com.kg.myapp.vo.MemberVO;

public interface IMemberRepository {

	void insertMember(MemberVO member);
	String getPassword(String userid);
	MemberVO getMember(String userid);
	int idChek(String userid);
	
}

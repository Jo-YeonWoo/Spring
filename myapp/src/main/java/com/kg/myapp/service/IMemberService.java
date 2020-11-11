package com.kg.myapp.service;

import com.kg.myapp.vo.MemberVO;

public interface IMemberService {
	
	public void insertMember(MemberVO member);
	String getPassword(String userid);
	MemberVO getMember(String userid);
	int idCheck(String userid);
	
}

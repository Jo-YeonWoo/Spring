package com.kg.myapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kg.myapp.dao.IMemberRepository;
import com.kg.myapp.vo.MemberVO;

@Service
public class MemberService implements IMemberService {
	
	@Autowired
	IMemberRepository memberRepository;
	
	@Autowired
	BCryptPasswordEncoder bpe;
	
	@Override
	public void insertMember(MemberVO member) {
		member.setPassword(bpe.encode(member.getPassword()));
		memberRepository.insertMember(member);
	}

	@Override
	public String getPassword(String userid) {
		return memberRepository.getPassword(userid);
	}
	
	@Override
	public MemberVO getMember(String userid) {
		return memberRepository.getMember(userid);
	}

	@Override
	public int idCheck(String userid) {
		return memberRepository.idChek(userid);
	}
	
}

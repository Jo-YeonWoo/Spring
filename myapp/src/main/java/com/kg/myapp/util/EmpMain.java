package com.kg.myapp.util;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.kg.myapp.service.IEmpService;

public class EmpMain {

	public static void main(String[] args) {
		
		AbstractApplicationContext con = new GenericXmlApplicationContext("app-config.xml");
		IEmpService empService = con.getBean("empService", IEmpService.class);
		empService.deleteEmp(100);
		con.close();
		
		/*
		System.out.println(empService.getEmpCount());
		System.out.println(empService.getEmpCount(30));
		System.out.println(empService.getEmpList());
		System.out.println(empService.getAllManagerId());
		con.close();
		
		EmpService empService = con.getBean(EmpService.class);
		System.out.println(empService.selectEmployee(100));
		
		List<EmpVO> empList = empService.selectAllEmployees();
		for(EmpVO emp : empList) {
			System.out.println(emp);
		}
		*/
	}

}

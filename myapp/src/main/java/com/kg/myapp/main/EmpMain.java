package com.kg.myapp.main;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.kg.myapp.service.IEmpService;

public class EmpMain {

	public static void main(String[] args) {
		AbstractApplicationContext con = 
				new GenericXmlApplicationContext("app-config.xml");
		IEmpService empService = con.getBean(IEmpService.class);
		empService.deleteEmp(100);
		con.close();
//		List<EmpVO> empList = empService.selectAllEmployees();
//		for(EmpVO emp : empList) {
//			System.out.println(emp);
//		}
	}
	
}

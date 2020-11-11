package com.kg.myapp.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.kg.myapp.dao.IEmpRepository;
import com.kg.myapp.vo.EmpVO;
import com.kg.myapp.vo.JobVO;

@Service
public class EmpService implements IEmpService{

	@Autowired
	@Qualifier("IEmpRepository")
	IEmpRepository empRepository;
	
	/*
	public EmpVO selectEmployee(int empId) {
		return empRepository.selectEmployee(empId);
	}
	
	public List<EmpVO> selectAllEmployees() {
		return empRepository.selectAllEmployees();
	}
	*/

	public EmpVO selectEmployee(int empId) {
		return empRepository.selectEmployee(empId);
	}

	public List<EmpVO> selectAllEmployees(){
		return empRepository.selectAllEmployees();
	}

	@Override
	public void deleteEmp(int empId) {
		empRepository.deleteJobHistory(empId);
		empRepository.updateManagers(empId);
		empRepository.deleteEmp(empId);
	}

	@Override
	public void deleteJobHistory(int empId) {
		empRepository.deleteJobHistory(empId);
	}

	@Override
	public void insertEmp(EmpVO emp) {
		empRepository.insertEmp(emp);
	}

	@Override
	public void updateEmp(EmpVO emp) {
		empRepository.deleteJobHistory(emp.getEmployeeId());
		empRepository.updateEmp(emp);
	}
	
	@Override
	public void updateManagers(int empId) {
		empRepository.updateManagers(empId);
	}

	@Override
	public int getEmpCount() {
		return empRepository.getEmpCount();
	}

	@Override
	public int getEmpCount(int deptId) {
		return empRepository.getEmpCount(deptId);
	}

	@Override
	public List<EmpVO> getEmpList() {
		return empRepository.getEmpList();
	}

	@Override
	public EmpVO getEmpInfo(int empId) {
		return empRepository.getEmpInfo(empId);
	}
	
	@Override
	public List<EmpVO> getEmpList(int deptId) {
		return empRepository.getEmpList(deptId);
	}

	@Override
	public List<EmpVO> getEmpSearch(String name) {
		return empRepository.getEmpSearch(name);
	}

	@Override
	public List<Map<String, Object>> getAllDeptId() {
		return empRepository.getAllDeptId();
	}

	@Override
	public List<JobVO> getAllJobId() {
		return empRepository.getAllJobId();
	}

	@Override
	public List<Map<String, Object>> getAllManagerId() {
		return empRepository.getAllManagerId();
	}

	@Override
	public int idCheck(int empId) {
		return empRepository.idCheck(empId);
	}

}

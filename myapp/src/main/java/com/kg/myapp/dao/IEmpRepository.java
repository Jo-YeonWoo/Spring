package com.kg.myapp.dao;

import java.util.List;
import java.util.Map;

import com.kg.myapp.vo.EmpVO;
import com.kg.myapp.vo.JobVO;

public interface IEmpRepository {

	public EmpVO selectEmployee(int empId);
	public List<EmpVO> selectAllEmployees();
	public void deleteEmp(int empId);
	public void deleteJobHistory(int empId);
	public void insertEmp(EmpVO emp);
	public void updateEmp(EmpVO emp);
	public void updateManagers(int empId);
	int getEmpCount();
	int getEmpCount(int deptId);
	public List<EmpVO> getEmpList();
	public List<EmpVO> getEmpList(int deptId);
	EmpVO getEmpInfo(int empId);
	public List<EmpVO> getEmpSearch(String name);
	public List<Map<String, Object>> getAllDeptId();
	public List<JobVO> getAllJobId();
	public List<Map<String, Object>> getAllManagerId();
	public byte[] getFile(int empId);
	public int idCheck(int empId);
	
}

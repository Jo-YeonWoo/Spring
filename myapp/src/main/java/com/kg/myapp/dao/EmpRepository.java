package com.kg.myapp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.kg.myapp.vo.EmpDetailVO;
import com.kg.myapp.vo.EmpVO;
import com.kg.myapp.vo.JobVO;

@Repository
public class EmpRepository implements IEmpRepository{

	@Autowired
	JdbcTemplate jdbcTemplate;

	//RowMapper<EmpVO> empMapper = new RowMapper<EmpVO>() {
	RowMapper<EmpVO> empMapper = new RowMapper<EmpVO>() {
		@Override
		public EmpVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			EmpVO emp = new EmpVO();
			emp.setEmployeeId(rs.getInt("employee_id"));
			emp.setFirstName(rs.getString("first_name"));
			emp.setLastName(rs.getString("last_name"));
			emp.setEmail(rs.getString("email"));
			emp.setPhoneNumber(rs.getString("phone_number"));
			emp.setHireDate(rs.getDate("hire_date"));
			emp.setJobId(rs.getString("job_id"));
			emp.setSalary(rs.getDouble("salary"));
			emp.setCommissionPct(rs.getDouble("commission_pct"));
			emp.setManagerId(rs.getInt("manager_id"));
			emp.setDepartmentId(rs.getInt("department_id"));
			emp.setEmpPic(rs.getBytes("emp_pic"));
			return emp;
		}
	};
	
	/*
	public EmpVO selectEmployee(int empId) {
		String sql = "select * from employees where employee_id=?";
		return jdbcTemplate.queryForObject(sql, empMapper, empId);
	}
	
	public List<EmpVO> selectAllEmployees() {
		String sql = "select * from employees";
		return jdbcTemplate.query(sql, empMapper);
	}
	*/

	@Override
	public EmpVO selectEmployee(int empId) {
		String sql = "select * from employees where employee_id=?";
		return jdbcTemplate.queryForObject(sql, empMapper, empId);
	}

	@Override
	public List<EmpVO> selectAllEmployees(){
		String sql = "select * from employees";
		return jdbcTemplate.query(sql, empMapper);
	}

	@Override
	public void deleteEmp(int empId) {
		String sql = "delete from employees where employee_id=?";
		jdbcTemplate.update(sql, empId);
	}

	@Override
	public void deleteJobHistory(int empId) {
		String sql = "delete from job_history where employee_id=?";
		jdbcTemplate.update(sql, empId);
	}	//트랜젝션과 관련된 개념

	@Override
	public void insertEmp(EmpVO emp) {
		String sql = "insert into employees "
				+ "values(?,?,?,?,?,sysdate,?,?,?,?,?,?,?)";
		jdbcTemplate.update(sql,emp.getEmployeeId(),
				emp.getFirstName(),emp.getLastName(),emp.getEmail(),
				emp.getPhoneNumber(),emp.getJobId(),emp.getSalary(),
				emp.getCommissionPct(),emp.getManagerId(),emp.getDepartmentId(),
				emp.getEmpPic(), emp.getfileSize()
				);
	}
	//insert, update, delete 같은 것은 '숫자'를 리턴 (1 행이 추가되었습니다 등과 같이)
	//그러나 굳이 리턴하지 않고 예외처리를 통해 처리도 가능

	@Override
	public void updateEmp(EmpVO emp) {
		String sql = "update employees set first_name=?, last_name=?, "
				+ "email=?, phone_number=?, hire_date=?, job_id=?, "
				+ "salary=?, commission_pct=?, manager_id=?, "
				+ "department_id=? where employee_id=?";
		jdbcTemplate.update(sql,emp.getFirstName(),
				emp.getLastName(),emp.getEmail(),emp.getPhoneNumber(),
				emp.getHireDate(),emp.getJobId(),emp.getSalary(),
				emp.getCommissionPct(),emp.getManagerId(),emp.getDepartmentId(),
				emp.getEmployeeId());
	}

	@Override
	public int getEmpCount() {
		String sql = "select count(*) from employees";
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
	@Override
	public int getEmpCount(int deptId) {
		String sql = "select count(*) from employees where department_id=?";
		return jdbcTemplate.queryForObject(sql, Integer.class, deptId);
	}

	@Override
	public List<EmpVO> getEmpList() {
		String sql = "select * from employees";
		return jdbcTemplate.query(sql, empMapper);
	}

	@Override
	public EmpVO getEmpInfo(int empId) {
		String sql = "select employee_id, first_name, last_name, email, phone_number,"
				+ "hire_date, e.job_id, job_title, salary, commission_pct, e.manager_id,"
				+ "manager_name, e.department_id, department_name, emp_pic, file_size "
				+ "from employees e "
				+ "left join jobs j "
				+ "on e.job_id=j.job_id "
				+ "left join departments d "
				+ "on e.department_id=d.department_id "
				+ "left join "
				+ "(select employee_id manager_id, first_name||' '||last_name manager_name "
				+ "from employees "
				+ "where employee_id in (select distinct manager_id from employees)) m "
				+ "on e.manager_id=m.manager_id "
				+ "where employee_id=?";
		return jdbcTemplate.queryForObject(sql, new RowMapper<EmpDetailVO>() {
			@Override
			public EmpDetailVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				EmpDetailVO emp = new EmpDetailVO();
				emp.setEmployeeId(rs.getInt("employee_id"));
				emp.setFirstName(rs.getString("first_name"));
				emp.setLastName(rs.getString("last_name"));
				emp.setEmail(rs.getString("email"));
				emp.setPhoneNumber(rs.getString("phone_number"));
				emp.setHireDate(rs.getDate("hire_date"));
				emp.setJobId(rs.getString("job_id"));
				emp.setSalary(rs.getDouble("salary"));
				emp.setCommissionPct(rs.getDouble("commission_pct"));
				emp.setManagerId(rs.getInt("manager_id"));
				emp.setDepartmentId(rs.getInt("department_id"));
				emp.setJobTitle(rs.getString("job_title"));
				emp.setManagerName(rs.getString("manager_name"));
				emp.setDepartmentName(rs.getString("department_name"));
				emp.setEmpPic(rs.getBytes("emp_pic"));
				emp.setFileSize(rs.getLong("file_size"));
				return emp;
			}
		}, empId);
	}
	
	//부서 번호 검색 시 검색한 부서 번호에 해당하는 사원 목록 출력
	@Override
	public List<EmpVO> getEmpList(int deptId) {
		String sql = "select * from employees where department_id=?";
		return jdbcTemplate.query(sql, empMapper, deptId);
	}
	
	@Override
	public void updateManagers(int empId) {
		String sql = "update (select * from employees where manager_id=? set manager_id=null";
		jdbcTemplate.update(sql, empId);
		sql = "update (select * from departments where manager_id=?) set manager_id=null";
		jdbcTemplate.update(sql, empId);
	}

	@Override
	public List<EmpVO> getEmpSearch(String name) {
		String sql = "select * from employees where first_name like ? or last_name like ?";
		return jdbcTemplate.query(sql, empMapper, name, name);
	}

	@Override
	public List<Map<String, Object>> getAllDeptId() {	//같은 key값에 다른 value를 온전히 뽑아내기 위해 List를 사용하는 것
		String sql = "select department_id as departmentId,"
				+ "department_name as departmentName from departments";
		return jdbcTemplate.queryForList(sql);
	}	//값이 2개면 굳이 객체롬 만들 필요 X, key와 value를 하나의 값으로 봐야 하기 때문에 map으로 묶고 list로 만듦
	
	@Override
	public List<JobVO> getAllJobId() {
		String sql = "select job_id as jobId, job_title as jobTitle from jobs";
		return jdbcTemplate.query(sql, new RowMapper<JobVO>() {
			@Override
			public JobVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				JobVO job = new JobVO();
				job.setJobId(rs.getString(1));
				job.setJobTitle(rs.getString(2));
				return job;
			}
		});
	}
	
	@Override
	public List<Map<String, Object>> getAllManagerId() {
		String sql = "select employee_id as managerId, "
				+ "first_name||' '||last_name as managerName "
				+ "from employees "
				+ "where employee_id in "
				+ "(select distinct manager_id from employees)";
		return jdbcTemplate.queryForList(sql);
	}
	
	/*
	public List<Map<String, Integer>> getUpdateCount(int empId) {
		String sql = "select (select count(*) from employees where manager_id=?) as empCount, "
				+ "(select count(*) from departments where manager_id=?) from dual";
		return jdbcTemplate.queryForObject(sql, empId, empId);
	}
	*/
	
	@Override
	public byte[] getFile(int empId) {
		return null;
	}

	@Override
	public int idCheck(int empId) {
		// TODO Auto-generated method stub
		return 0;
	}

}
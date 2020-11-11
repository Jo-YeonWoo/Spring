package com.kg.myapp.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kg.myapp.service.IEmpService;
import com.kg.myapp.vo.EmpDetailVO;
import com.kg.myapp.vo.EmpVO;

//@Controller가 없으면 주소 매핑이 불가능 즉, 이 어노테이션이 있어야 requestMapping이 가능
@Controller	//반복되는 주소는 Controller에 설정해 놓으면 반복해서 쓰지 않아도 됨
public class EmpController {

	@Autowired
	IEmpService empService;
	
	/*
	@Autowired
	private EmpValidator empValidator;
	*/
	
	//@RequestMapping(value="/emp")	//@RequestMapping(value="(매핑시키고 싶은 주수 작성)", method="GET | POST[쓰지 않으면 디폴트로 GET]")
	@GetMapping(value="/emp")
	public String mainPage(Model model) {
		model.addAttribute("message","Hello! Welcome to EmpApp.");
		return "home";
	}
	
	//사원의 전체 수 세기
	@GetMapping(value="/emp/count")
	public String empCount(@RequestParam(value="deptId",required=false,defaultValue="0")int deptId, Model model) {
		if(deptId==0) {
			model.addAttribute("count", empService.getEmpCount());
		}else {
			model.addAttribute("count", empService.getEmpCount(deptId));
		}
		return "emp/count";
	}
	
	//사원 리스트
	@GetMapping(value="/emp/list")
	public void empList(Model model) {
		model.addAttribute("empList", empService.getEmpList());
	}
	
	//사원 정보 상세 조회
	@GetMapping(value="/emp/{employeeId}")
	public String empView(@PathVariable int employeeId, Model model) {
		model.addAttribute("emp",(EmpDetailVO)empService.getEmpInfo(employeeId));
		return "emp/view";
	}
	
	//부서 번호 검색 시 검색한 부서 번호에 해당하는 사원 목록 출력
	@GetMapping(value="/emp/deptList")
	public String empDeptList(int deptId, Model model) {
		model.addAttribute("empList",empService.getEmpList(deptId));
		return "emp/list";
	}
	
	//사원 이름 검색 시 검색한 이름이 포함되는 사원 목록 출력
	@GetMapping(value="/emp/nameSearch")
	public String empNameSearch(String name, Model model) {
		name = "%"+name+"%";
		model.addAttribute("empList",empService.getEmpSearch(name));
		return "emp/list";
	}
	
	//사원 정보 입력
	/*
	@GetMapping(value="/emp/insert")
	public void empInsert(Model model) {
		model.addAttribute("jobList", empService.getAllJobId());
		model.addAttribute("manList", empService.getAllManagerId());
		model.addAttribute("deptList",empService.getAllDeptId());
		model.addAttribute("message", "insert");
	}
	
	@PostMapping(value="/emp/insert")
	public String empInsert(EmpVO emp,Model model) {
		empService.insertEmp(emp);
		return "redirect:/emp/list";
	}
	*/
	
	@GetMapping(value="/emp/insert")
	public String insertEmp(Model model) {
		model.addAttribute("emp", new EmpVO());
		model.addAttribute("jobList", empService.getAllJobId());
		model.addAttribute("manList", empService.getAllManagerId());
		model.addAttribute("deptList", empService.getAllDeptId());
		model.addAttribute("message", "insert");
		return "emp/insert";
	}
	
	@PostMapping(value="/emp/insert")
	public String insertEmp(@ModelAttribute("emp") @Valid EmpVO emp, BindingResult result,
			Model model, @RequestParam MultipartFile file,RedirectAttributes redirectAttributes) {
		if(result.hasErrors()) {
			//model.addAttribute("emp", new EmpVO());
			model.addAttribute("jobList", empService.getAllJobId());
			model.addAttribute("manList", empService.getAllManagerId());
			model.addAttribute("deptList", empService.getAllDeptId());
			model.addAttribute("message", "insert");
			return "emp/insert";
		}
		if((file != null) && (!file.isEmpty())) {
			try {
				emp.setEmpPic(file.getBytes());
				emp.setFileSize(file.getSize());
				//EmpVO에 추가, DB에 열 추가
			} catch (IOException e) {
				e.printStackTrace();
				redirectAttributes.addFlashAttribute("message", e.getMessage());
				redirectAttributes.addFlashAttribute("exception", e);
				throw new RuntimeException();
			} 
		}
		empService.insertEmp(emp);
		redirectAttributes.addFlashAttribute("message", "회원 저장 완료");
		return "redirect:/emp/list";
	}
	
	//사원 정보 수정
	@GetMapping(value="/emp/update/{empId}")
	public String empUpdate(@PathVariable int empId, Model model) {
		model.addAttribute("emp",empService.getEmpInfo(empId));
		model.addAttribute("jobList", empService.getAllJobId());
		model.addAttribute("manList", empService.getAllManagerId());
		model.addAttribute("deptList",empService.getAllDeptId());
		model.addAttribute("message","update");
		return "emp/insert";
	}
	
	@PostMapping(value="/emp/update")
	public String updateEmp(EmpVO emp, Model model) {
		empService.updateEmp(emp);
		return "redirect:/emp/" + emp.getEmployeeId();
	}
	
	//사원 정보 삭제
	@GetMapping(value="/emp/delete")
	public String deleteEmp(int empId, Model model) {
		model.addAttribute("emp", empService.getEmpInfo(empId));
		model.addAttribute("count", empService.getEmpCount(empId));
		//model.addAttribute("deptCount");
		return "emp/delete";
	}
	
	//TimeTracer : 예외처리를 컨트롤러 쪽으로 보내기
	//Controller에 적는 건 보통 RuntimeException이 될 것
	@ExceptionHandler(RuntimeException.class)
	public String runtimeException(HttpServletRequest request, Exception ex, Model model) {
		model.addAttribute("url", request.getRequestURI());
		model.addAttribute("exception", ex);
		return "error/runtime";
	}
	
	@GetMapping(value="/emp/pic/{empId}")
	public ResponseEntity<byte[]> getPicture(@PathVariable int empId){
		EmpVO emp = empService.getEmpInfo(empId);
		final HttpHeaders headers = new HttpHeaders();
		if(emp.getEmpPic() != null) {
			headers.setContentType(new MediaType("image", "jpeg"));
			headers.setContentDispositionFormData("attachment", "프로필 사진");
			headers.setContentLength(emp.getfileSize());	//파일 사이즈를 맞추지 않으면 사진이 떠 있다가 사라짐
			return new ResponseEntity<byte[]>(emp.getEmpPic(), headers, HttpStatus.OK);
		} else {
			return new ResponseEntity<byte[]>(emp.getEmpPic(), headers, HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping(value="/emp/check")
	@ResponseBody
	public String idCheck(int empId) {
		System.out.println(empService.idCheck(empId));
		return empService.idCheck(empId) == 0 ? "true" : null;
		//"false"로 돌리면 문자열 값이 그대로 들어가긴 하나 "" 때문에 값이 있는 걸로 간주되어 true가 됨 
	}
	
	
	/*
	//Validation
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(empValidator);
	}
	*/
	
}
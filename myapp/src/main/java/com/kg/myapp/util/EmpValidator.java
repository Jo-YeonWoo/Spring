package com.kg.myapp.util;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.kg.myapp.vo.EmpVO;

@Component
public class EmpValidator implements Validator, InitializingBean {
	
	private javax.validation.Validator validator;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		ValidatorFactory vFactory = Validation.buildDefaultValidatorFactory();
		validator = vFactory.usingContext().getValidator();
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return EmpVO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Set<ConstraintViolation<Object>> violations = validator.validate(target);
		for(ConstraintViolation<Object> violation : violations) {
			String propertyPath = violation.getPropertyPath().toString();
			String message = violation.getMessage();
			errors.rejectValue(propertyPath, message, message);
		}
		
		EmpVO emp = (EmpVO)target;
		Integer employeeId = emp.getEmployeeId();
		ValidationUtils.rejectIfEmpty(errors, "lastName", "emp.lastName.empty", "성을 입력하세요.");
		if(employeeId != null && employeeId < 1) {
			errors.rejectValue("employeeId", "emp.employeeId.lessThenOne", "사원번호가 1보다 작습니다.");
		}
	}

}

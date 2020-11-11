<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<meta charset="UTF-8">
<title>Employee ${message}</title>
</head>
<body>
	<h1>사원 정보 ${message eq "insert" ? "입력" : "수정" }</h1>
	<form:form action="./${message}" method="post" modelAttribute="emp" enctype="multipart/form-data">
		<table border="1">
			<tr>
				<th>Employee_id</th>
				<td><form:input path="employeeId" />
				<form:errors path="employeeId" /><button id="check">중복 검사</button></td>
			</tr>
			<tr>
				<th>First_name</th>
				<td><form:input path="firstName" />
				<form:errors path="firstName" /></td>
			</tr>
			<tr>
				<th>Last_name</th>
				<td><form:input path="lastName" />
				<form:errors path="lastName" /></td>
			</tr>
			<tr>
				<th>Email</th>
				<td><form:input path="email" />
				<form:errors path="email" /></td>
			</tr>
			<tr>
				<th>Phone_number</th>
				<td><form:input path="phoneNumber" />
				<form:errors path="phoneNumber" /></td>
			</tr>
			<tr>
				<th>Hire_date</th>
				<td><form:input path="hireDate" type="date" required="required" />
				<form:errors path="hireDate" /></td>
			</tr>
			<tr>
				<th>Job_id</th>
				<td>
					<form:select path="jobId">
						<form:options var="job" items="${jobList}" itemLabel="jobTitle"
						itemValue="jobId" />
					</form:select>
				</td>
			</tr>
			<tr>
				<th>Salary</th>
				<td><form:input path="salary" />
				<form:errors path="salary" /></td>
			</tr>
			<tr>
				<th>Commission_pct</th>
				<td><form:input path="commissionPct" type="number" step="0.05" />
				<form:errors path="commissionPct" /></td>
			</tr>
			<tr>
				<th>Manager_id</th>
				<td>
					<form:select path="managerId">
						<c:forEach var="man" items="${manList}">
							<option value="${man.managerId}">${man.managerName}</option>
						</c:forEach>
					</form:select>
				</td>
			</tr>
			<tr>
				<th>Department_id</th>
				<td>
					<form:select path="departmentId">
						<c:forEach var="dept" items="${deptList}">
							<option value="${dept.departmentId}">${dept.departmentName}</option>
						</c:forEach>
					</form:select>
				</td>
			</tr>
			<tr>
				<td>프로필 사진(5Mb 이하)</td>
				<td><input type=file name=file></td>
			</tr>
			<tr>
				<th colspan=2>
					<input type=submit value="입력" id=submit>
					<input type=reset value="취소">
				</th>
			</tr>
		</table>
	</form:form>
	<script>
		$(function() {
			var ck = false;
			$("#check").on("click", function() {
				if($("#employeeId").val()) {
					$.ajax({
						url : "/myapp/emp/check",
						type : "post",
						data : {empId : $("#employeeId").val()},
						dataType : "text",
						success : function(check) {
							console.log(check);
							if(check) {
								alert("중복되지 않습니다.");
								$("#check").remove();
								$("#employeeId").attr("readonly", true);
								ck != ck;
							} else {
								alert("사원번호가 중복됩니다.");
							}
							return false;
						},
						error : function() {
							alert("ajax에 문제가 있습니다.");
							return false;
						}
					});
				} else {
					alert("값이 있어야 합니다.");
					return false;
					//form에서 alert 확인 혹은 취소를 눌렀을 때 다시 정상 동작이 되게 하려면 return false가 적혀야 함
					//그렇지 않으면, true로 보내져 누르는 순간 그대로 form이 전송될 것
				}
			});
			//var 변수 = function() {} : 실행할 함수의 값이 필요할 땐 사용 X | 함수의 코드가 들어가는 것
			//						 	-> 주로 반복문 등에 사용됨
			//function 함수이름() {}
			$("#submit").on("click", function() {
				if(ck) {
					
				} else {
					alert("중복검사가 먼저 진행되어야 합니다.");
					return false;
				}
			});
		});
	</script>
</body>
</html>
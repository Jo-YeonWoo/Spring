<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<meta charset="UTF-8">
<title>회원가입</title>
</head>
<body>
	<h1>회원 정보 입력</h1>
	<form action="/myapp/member/insert" method=post enctype=multipart/form-data>
		<table>
			<tr>
				<td>아이디</td>
				<td><input type=text name=userid id=userid><button id=ck>중복검사</button></td>
			</tr>
			<tr>
				<td>비밀번호</td>
				<td><input type=password name=password></td>
			</tr>
			<tr>
				<td>전화번호</td>
				<td><input type=text name=tel pattern="^[0][1][0-9](-|/s)\d{4}(-|/s)\d{4}"></td>
			</tr>
			<tr>
				<td>프로필 사진</td>
				<td><input type=file name=file></td>
			</tr>
			<tr>
				<td>아이디</td>
				<td><input type=text name=userid></td>
			</tr>
			<tr>
				<td>
					<input type=submit value=가입 id=submit>
					<input type=reset value=취소>
				</td>
			</tr>
		</table>
	</form>
	<script>
		$(function() {
			var ck = false;
			$("#ck").on("click", function() {
				if($("#userid").val()) {
					$.ajax({
						url : "/myapp/member/check",
						type : "post",
						data : {userid : $("#userid").val()},
						dataType : "text",
						success : function(check) {
							if(check) {
								alert("사용 가능한 아이디입니다.");
								$("#ck").remove();
								$("#userid").attr("readonly", true);
								ck = !ck;
							} else {
								alert("이미 사용 중인 아이디입니다.");
							}
							return false;
						},
						error : function() {
							alert("ajax에 문제가 있습니다.");
							return false;
						}
					});
				} else {
					alert("값이 없습니다.");
					return false;
				}
			});
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
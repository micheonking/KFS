<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<% 
%>
</head>
<script language="javascript">
//opener관련 오류가 발생하는 경우 아래 주석을 해지하고, 사용자의 도메인정보를 입력합니다. ("주소입력화면 소스"도 동일하게 적용시켜야 합니다.)
//document.domain = "abc.go.kr";

function init(){
	window.open(location.protocol + "//" + location.host + "/zipAddr/SearchAddr.jsp","abcd");
}

function jusoCallBack(roadAddrPart1, roadAddrPart2, addrDetail, zipCode){
	roadAddrPart1;//도로명주소
	roadAddrPart2;//동명
	addrDetail;//입력한 주소
	zipCode;//우편번호
	console.log(roadAddrPart1);
	console.log(roadAddrPart2);
	console.log(addrDetail);
	console.log(zipCode);
	window.close();
}
</script>
<body onload="init();">

</body>
</html>
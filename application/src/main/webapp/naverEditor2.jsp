<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<style type="text/css">
<%--
div{
  font-family: "굴림", "돋움", dotum, gulim, arial, helvetica, sans-serif;
  font-size : 12px;
}
--%>
</style>

<script type="text/javascript" src="<%=request.getContextPath()%>/smarteditor/js/lib/jquery3.3.1.js"></script>
<%--
<script type="text/javascript" src="<%=request.getContextPath()%>/smarteditor/js/service/HuskyEZCreator.js" charset="utf-8"></script>
 --%>


<script>

function setHtml(str){
	$("#test1").html(str);
}
</script>
<meta charset="UTF-8">
<title></title>
</head>
<body>
    <div id="test1"></div>
</body>
</html>
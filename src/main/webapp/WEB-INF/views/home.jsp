<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<!doctype html>
<html lang="ko">
<head>
	<meta charset='utf-8' />
	<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
	<title>Spring Example</title>
	<!-- Bootstrap + jQuery -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
	<script src="http://code.jquery.com/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<!--// Bootstrap + jQuery -->
</head>
<body>
	<div class="container">
		<h1 class="page-header">원하는 분야를 선택하세요</h1>
		
<%-- 		<a href="${pageContext.request.contextPath}/param/home.do" class="btn btn-primary btn-block">Send Parameter</a>
		
		<a href="${pageContext.request.contextPath}/cookie/write.do" class="btn btn-success btn-block">Cookie</a>
		
		<a href="${pageContext.request.contextPath}/session/write.do" class="btn btn-info btn-block">Session</a>
		
		<a href="${pageContext.request.contextPath}/calc.do" class="btn btn-warning btn-block">AOP</a>
		
		<a href="${pageContext.request.contextPath}/mail/write.do" class="btn btn-danger btn-block">Mail Helper</a>
		
		<a href="${pageContext.request.contextPath}/upload/upload.do" class="btn btn-primary btn-block">Upload Helper</a> --%>
		
		<a href="${pageContext.request.contextPath}/professor/prof_list.do" class="btn btn-success btn-block">Professor List</a>
		
		<a href="${pageContext.request.contextPath}/student/stud_list.do" class="btn btn-info btn-block">Student List</a>
		
		<a href="${pageContext.request.contextPath}/department/dept_list.do" class="btn btn-warning btn-block">Department List</a>
	</div>
</body>
</html>
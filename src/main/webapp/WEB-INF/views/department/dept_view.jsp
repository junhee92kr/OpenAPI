<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
		<h1 class='page-header'>학과 상세 보기</h1>
		
		<!-- 조회결과를 출력하기 위한 표 -->
		<table class="table table-bordered">
			<tbody>
				<tr>
					<th class="info text-center" width="130">학과번호</th>
					<td>${item.deptno}</td>
				</tr>
				<tr>
					<th class="info text-center">이름</th>
					<td>${item.dname}</td>
				</tr>
				<tr>
					<th class="info text-center">위치</th>
					<td>${item.loc}</td>
				</tr>

			</tbody>
		</table>
		
		<!-- 버튼 -->
		<div class="text-center">
			<a href="${pageContext.request.contextPath}/department/dept_list.do" class="btn btn-primary">목록</a>
			<a href="${pageContext.request.contextPath}/department/dept_add.do" class="btn btn-info">추가</a>
			<a href="${pageContext.request.contextPath}/department/dept_edit.do?deptno=${item.deptno}" class="btn btn-warning">수정</a>
			<a href="${pageContext.request.contextPath}/department/dept_delete.do?deptno=${item.deptno}" class="btn btn-danger">삭제</a>
		</div>
	</div>
</body>
</html>
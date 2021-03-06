<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html>
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
		<div class="page-header clearfix">
			<h1 class='pull-left'>학과목록</h1>
			<!-- 검색폼 + 추가버튼 -->
			<div style='margin-top: 30px;' class="pull-right">
				<form method='get' action='${pageContext.request.contextPath}/department/dept_list.do' style="width: 300px;">
					<div class="input-group">
						<input type="text" name='keyword' class="form-control" 
							placeholder="학과이름 검색" value="${keyword}" />
						<span class="input-group-btn">
							<button class="btn btn-success" type="submit">
								<i class='glyphicon glyphicon-search'></i>
							</button>
							<a href="${pageContext.request.contextPath}/department/dept_add.do" class="btn btn-primary">학과추가</a>
						</span>
					</div>
				</form>
			</div>
		</div>

		<!-- 조회결과를 출력하기 위한 표 -->
		<table class="table">
			<thead>
				<tr>
					<th class="info text-center">학과번호</th>
					<th class="info text-center">이름</th>
					<th class="info text-center">위치</th>
				</tr>
			</thead>
			<tbody>
		    	<c:choose>
		    		<c:when test="${fn:length(deptList) > 0}">
		    			<c:forEach var="item" items="${deptList}">
		    				<tr>
					            <td class="text-center">${item.deptno}</td>
					             <td>
					            	<c:url var="readUrl" value="/department/dept_view.do">
					            		<c:param name="deptno" value="${item.deptno}" />
					            	</c:url>
					            	<a href="${readUrl}">${item.dname}</a>
					            </td> 
					            <td class="text-center">${item.loc}</td>
				        	</tr>
		    			</c:forEach>
		    		</c:when>
		    		<c:otherwise>
		    			<tr>
				            <td colspan="8" class="text-center" style="line-height: 100px;">
				                조회된 데이터가 없습니다.</td>
				        </tr>
		    		</c:otherwise>
		    	</c:choose>
			</tbody>
		</table>

		<!-- 페이지 번호 -->
		<nav class="text-center">
			<ul class="pagination">
				<!-- 이전 그룹으로 이동 -->
				<c:choose>
					<c:when test="${page.prevPage > 0}">
						<!-- 이전 그룹에 대한 페이지 번호가 존재한다면? -->
						<!-- 이전 그룹으로 이동하기 위한 URL을 생성해서 "prevUrl"에 저장 -->
						<c:url var="prevUrl" value="/department/dept_list.do">
							<c:param name="keyword" value="${keyword}"></c:param>
							<c:param name="page" value="${page.prevPage}"></c:param>
						</c:url>

						<li><a href="${prevUrl}">&laquo;</a></li>
					</c:when>

					<c:otherwise>
						<!-- 이전 그룹에 대한 페이지 번호가 존재하지 않는다면? -->
						<li class='disabled'><a href="#">&laquo;</a></li>
					</c:otherwise>
				</c:choose>

				<!-- 페이지 번호 -->
				<!-- 현재 그룹의 시작페이지~끝페이지 사이를 1씩 증가하면서 반복 -->
				<c:forEach var="i" begin="${page.startPage}" end="${page.endPage}" step="1">
					<!-- 각 페이지 번호로 이동할 수 있는 URL을 생성하여 page_url에 저장 -->
					<c:url var="pageUrl" value="/department/dept_list.do">
						<c:param name="keyword" value="${keyword}"></c:param>
						<c:param name="page" value="${i}"></c:param>
					</c:url>

					<!-- 반복중의 페이지 번호와 현재 위치한 페이지 번호가 같은 경우에 대한 분기 -->
					<c:choose>
						<c:when test="${page.page == i}">
							<li class='active'><a href="#">${i}</a></li>
						</c:when>
						<c:otherwise>
							<li><a href="${pageUrl}">${i}</a></li>
						</c:otherwise>
					</c:choose>
				</c:forEach>

				<!-- 다음 그룹으로 이동 -->
				<c:choose>
					<c:when test="${page.nextPage > 0}">
						<!-- 다음 그룹에 대한 페이지 번호가 존재한다면? -->
						<!-- 다음 그룹으로 이동하기 위한 URL을 생성해서 "nextUrl"에 저장 -->
						<c:url var="nextUrl" value="/department/dept_list.do">
							<c:param name="keyword" value="${keyword}"></c:param>
							<c:param name="page" value="${page.nextPage}"></c:param>
						</c:url>

						<li><a href="${nextUrl}">&raquo;</a></li>
					</c:when>

					<c:otherwise>
						<!-- 이전 그룹에 대한 페이지 번호가 존재하지 않는다면? -->
						<li class='disabled'><a href="#">&raquo;</a></li>
					</c:otherwise>
				</c:choose>
			</ul>
		</nav>
	</div>
</body>
</html>




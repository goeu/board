<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<body>
	<h1>List Page</h1>
	<ul class="list">
		<li>
			<div>
				<span>번호</span> <span>제목</span> <span>작성자</span> <span>작성일</span>
			</div>
		</li>
		<c:forEach items="${list}" var="board">
			<li>
				<span>${board.bno }</span>
			<span><a href="/board/read?bno=${board.bno}&page=${pm.paging.page}&amount=${pm.paging.amount}">
				${board.title}
			</a></span>
			<span>${board.regdate}</span>
			</li>
		</c:forEach>
	</ul>
<style>
.paging{
	list-style:none;
}
.paging li {
	float : left;
	margin-right: 0.3em;
	border : 1px solid blue;
	border-radius: 0.3em;	
}
.current{
	background-color: red;
}
</style>
<ul class="paging">
	<c:if test="${pm.prev}">
	<li><a href="/board/list?page=${pm.start-1}&amount=${pm.paging.amount}">이전</a></li>
	</c:if>
		<c:forEach begin="${pm.start}" end="${pm.end}" var="num">
		<li class='${pm.paging.page == num? "current":"" }'>
		<a href="/board/list?page=${num}&amount=${pm.paging.amount}">${num }</a></li>
	</c:forEach>
	
		<c:if test="${pm.next}">
		<li><a href="/board/list?page=${pm.end+1}&amount=${pm.paging.amount}">다음</a></li>
	</c:if>
	
	
</ul>
</body>
</html>
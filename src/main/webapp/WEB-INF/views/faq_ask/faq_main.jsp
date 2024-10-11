<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>고객센터</title>
<style type="text/css">
* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
}

body {
	font-family: Arial, sans-serif;
	background-color: #f9f9f9;
	color: #333;
}

.container {
	display: flex;
	width: 80%;
}

.sidebar {
	width: 350px;
	background-color: #fff;
	border-right: 1px solid #ddd;
	padding: 20px;
}

.sidebar h2 {
	font-size: 18px;
	margin-bottom: 20px;
}

.sidebar ul {
	list-style: none;
	padding: 0;
	margin-bottom: 30px;
}

.sidebar ul li {
	margin-bottom: 10px;
}

.sidebar ul li a {
	text-decoration: none;
	color: #333;
	display: block;
	padding: 10px;
	background-color: #f1f1f1;
	border-radius: 4px;
	transition: background-color 0.3s;
}

.sidebar ul li a:hover {
	background-color: #ddd;
}

.contact-info, .account-info {
	margin-bottom: 30px;
}

.contact-info h3, .account-info h3 {
	font-size: 16px;
	margin-bottom: 10px;
}

.contact-info p, .account-info p {
	margin-bottom: 5px;
}

.main-content {
	flex-grow: 1;
	padding: 20px;
	background-color: #fff;
}

.main-content h1 {
	font-size: 24px;
	margin-bottom: 20px;
}

.search-bar {
	display: flex;
	margin-bottom: 20px;
}

.search-bar input {
	width: 300px;
	padding: 10px;
	border: 1px solid #ddd;
	border-radius: 4px;
	margin-right: 10px;
}

.search-bar button {
	padding: 10px 20px;
	background-color: #333;
	color: #fff;
	border: none;
	border-radius: 4px;
	cursor: pointer;
}

.search-bar button:hover {
	background-color: #555;
}

.faq-table {
	width: 100%;
	border-collapse: collapse;
	margin-top: 20px;
}

.faq-table caption {
	font-size: 18px;
	margin-bottom: 10px;
	text-align: left;
}

.faq-table th, .faq-table td {
	border: 1px solid #ddd;
	padding: 10px;
	text-align: center;
}

.faq-table th {
	background-color: #f4f4f4;
}

.faq-table tbody tr:hover {
	background-color: #f9f9f9;
}

.trlink:hover {
	cursor: pointer;
	background-color: #ddd;
}

.pagination {
	text-align: center;
	margin-top: 20px;
}

.pagination a {
	margin: 0 5px;
	text-decoration: none;
	padding: 5px 10px;
	border: 1px solid #ddd;
	color: #333;
}

.pagination a:hover {
	background-color: #ddd;
}

.pagination .current {
	font-weight: bold;
	color: red;
}
/* 플로팅 메뉴 스타일 */
#floating-menu {
	position: fixed;
	right: 30px;
	top: 250px; /* 상단에서 150px 떨어짐 */
	z-index: 600;
	background-color: #fff;
	border: 1px solid #ddd;
	border-radius: 10px;
	padding: 20px;
}

.main-content {
	margin-right: 150px;
}

#floating-menu ul {
	list-style: none;
	padding: 0;
	margin: 0;
}

#floating-menu ul li {
	margin-bottom: 10px;
}

#floating-menu ul li a {
	text-decoration: none;
	color: #333;
	padding: 10px 20px;
	display: block;
	border: 1px solid #ddd;
	border-radius: 5px;
	text-align: center;
	transition: background-color 0.3s;
}

#floating-menu ul li a:hover {
	background-color: #f4f4f4;
}

.scroll-button {
	color: #333;
	text-align: center;
	padding: 10px 20px;
	border: 1px solid #ddd;
	font-size: 20px;
	border-radius: 5px;
	cursor: pointer;
	margin-bottom: 8px;
}

.scroll-button:hover {
	background-color: #f4f4f4;
}
</style>
</head>
<body>
	<div class="container">
		<!-- 사이드 메뉴바 -->
		<aside class="sidebar">
			<h2>고객센터</h2>
			<ul>
				<li><a href="faq_community">고객센터</a></li>
				<li><a href="gongjiboard">공지사항</a></li>
				<li><a href="faqin">1:1 문의하기</a></li>
				<li><a href="faqout">문의 내역</a></li>
				<li><a href="faq">FAQ</a></li>
				<!-- 관리자만 확인하기 버튼임 -->
				<% 
					Boolean FAQinput = (Boolean) session.getAttribute("adminloginstate");
					if (FAQinput != null && FAQinput) {
				%>
					<li><a href="FAQ_in">FAQ 글 쓰기</a></li>
				<% 
					}
				%>
			</ul>
			<div class="contact-info">
				<h3>고객상담센터</h3>
				<p>070-7777-7777</p>
				<p>안녕하세요@naver.com</p>
				<p>운영 시간: 11:00 ~ 19:00 (연중무휴)</p>
			</div>
			<div class="account-info">
				<h3>은행계좌 안내</h3>
				<p>777777-77-777777</p>
				<p>행복은행 (예금주: 행복이)</p>
			</div>
		</aside>

		<!-- 오른쪽 플로팅 메뉴 -->
		<div id="floating-menu">
			<ul>
				<li><a href="#">CART</a></li>
				<li><a href="#">KAKAO</a></li>
				<li><a href="#">CREDIT CARD</a></li>
				<li><a href="#">EMS</a></li>
			</ul>
		</div>

		<!-- 메인 콘텐츠 -->
		<main class="main-content">
			<h1>자주 묻는 질문</h1>
		
		<!-- 검색 바 -->
			<form action="faq_main_serch" method="post" class="search-bar">
				<!-- 기간 선택 -->
			    <select name="faqkey1" id="faqkey1">
			        <option value="">기간 선택</option>
			        <option value="1">1일</option>
			        <option value="7">1주일</option>
			        <option value="30">1개월</option>
			        <option value="90">3개월</option>
			    </select>
			
			    <!-- 검색 조건 선택 -->
			    <select name="faqkey2" id="faqkey2">
			        <option value="">검색 조건 선택</option>
			        <option value="title">제목</option>
			        <option value="fcontents">내용</option>
			        <option value="nickname">글쓴이</option>
			    </select>
			
			    <!-- 검색어 입력 -->
			    <input type="text" name="faqvalue" id="faqvalue" placeholder="검색어를 입력하세요" required>
			
			    <!-- 검색 버튼 -->
			    <button type="submit">검색</button>
			</form>
			
		<!-- 메인 게시판 -->
			<table class="faq-table">
				<caption>BEST FAQ</caption>
				<thead>
					<tr>
						<th>번호</th>
						<th>분류</th>
						<th>제목</th>
						<th>조회수</th>
					</tr>
				</thead>
				<c:forEach items="${bestfaq}" var="bestfaq">
					<tbody>
						<tr onclick="location.href='faqdetail?cnum=${bestfaq.cnum}'" class="trlink">
							<td>${bestfaq.cnum}</td>
							<td>${bestfaq.tab}</td>
							<td>${bestfaq.title}</td>
							<td>${bestfaq.faqcnt}</td>
						</tr>
					</tbody>
				</c:forEach>
			</table>
		</main>
	</div>
</body>
</html>

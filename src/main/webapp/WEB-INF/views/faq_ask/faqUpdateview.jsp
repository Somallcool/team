<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
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
/* 탑버튼, 바텀버튼 */
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
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class="container">
		<!-- 사이드 메뉴바 -->
		<aside class="sidebar">
			<h2>고객센터</h2>
			<ul>
				<li><a href="faq_community">고객센터</a></li>
				<li><a href="gongjiboard">공지사항</a></li>
				<% 
					Boolean FAQmember = (Boolean) session.getAttribute("loginstate");
					if (FAQmember != null && FAQmember) {
				%>
					<li><a href="faqin">1:1 문의하기</a></li>
				<%
					}
				%>
				<li><a href="faqout">문의 내역</a></li>
				<li><a href="faq">FAQ</a></li>
			</ul>
			<div class="contact-info">
				<h3>고객상담센터</h3>
				<p>070-7777-7777</p>
				<p>example@naver.com</p>
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
				<li><a href="cart">장바구니</a></li>
				<li><a href="https://open.kakao.com/o/suixDsUg">KAKAO문의</a></li>
				<li><a href="#">배송조회</a></li>
				<li><a href="#">최근 본 상품</a></li>
				<li><a href="#">관심상품</a></li>
				<li><a href="myinfo">마이페이지</a></li>
			</ul>
            <div class="scroll-button" onclick="window.scrollTo({top: 0, behavior: 'smooth'});">△</div>
            <div class="scroll-button" onclick="window.scrollTo({top: document.body.scrollHeight, behavior: 'smooth'});">▽</div>
		</div>
	<form action="faqupdate2" method="post" enctype="multipart/form-data">
		<table>
			<caption>
				<span>문의글 수정</span>
			</caption>
			<tr>
				<td><input type="number" name="cnum" value="${dto.cnum}"
					readonly></td>
			</tr>
			<tr>
				<th>문의 종류</th>
				<td><select name="tab">
						<option value="회원관련 문의">회원관련 문의</option>
						<option value="이벤트/혜택">이벤트/혜택</option>
						<option value="상품옵션 문의">상품옵션</option>
						<option value="교환/환불 문의">교환/환불</option>
						<option value="배송 문의">배송 문의</option>
						<option value="기타 문의">기타 문의</option>
				</select></td>
			</tr>
			<tr>
				<th>제목</th>
				<td><input type="text" name="title" value="${dto.title}"></td>
			</tr>
			<tr>
				<th>작성자</th>
				<td><input type="text" name="nickname" value="${dto.nickname}"></td>
			</tr>
			<tr>
				<th>문의 내용</th>
				<td><textarea rows="10" cols="25" name="fcontents">${dto.fcontents}</textarea>
				</td>
			</tr>
			<tr>
				<th>첨부이미지</th>

				<td><input type="file" name="fimage1"> <input
					type="file" name="fimage2"> <input type="file"
					name="fimage3"></td>
				<td><img src="./image/${dto.fimage1}" width="80px"
					height="70px"> <img src="./image/${dto.fimage2}" width="80px"
					height="70px"> <img src="./image/${dto.fimage3}" width="80px"
					height="70px"></td>
			</tr>
			<tr>
				<!-- 공개/비공개 체크박스 -->
			    <td>
				    <label for="openclose">공개 여부:</label>
				    <input type="checkbox" name="openclose" value="공개"> 공개
				    <input type="checkbox" name="openclose" value="비공개"> 비공개
				</td>
			</tr>
			<tr>
				<td><input type="submit" value="수정 완료"></td>
			</tr>
		</table>
	</form>
</div>	
</body>
</html>
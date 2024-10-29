<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Do+Hyeon&family=Gowun+Batang&family=Nanum+Gothic&family=Noto+Sans+KR:wght@100..900&family=Song+Myung&display=swap" rel="stylesheet">
<script>
    window.addEventListener('scroll', function() {
        var floatingMenu = document.getElementById('floating-menu');  // 사이드바 요소
        var carousel = document.getElementById('myCarousel');  // 캐러셀 요소
        var carouselBottom = carousel.getBoundingClientRect().bottom;  // 캐러셀의 하단 위치

        // 캐러셀의 끝부분이 화면 위로 올라가면 사이드바를 보이게 설정
        if (carouselBottom <= 0) {
            floatingMenu.style.position = 'fixed';
            floatingMenu.style.top = '250px';  // 고정된 위치 설정
            floatingMenu.style.visibility = 'visible';  // 사이드바 보이게 함
        } else {
            // 캐러셀이 화면에 있을 때는 사이드바를 보이지 않도록 설정
            floatingMenu.style.visibility = 'visible';
        }
    });
    
    function showLoginAlert() {
        alert("로그인 후 이용해주세요!");
    }
</script>


<% 
    Boolean loginState = (Boolean) session.getAttribute("loginstate")||(Boolean) session.getAttribute("adminloginstate");
    System.out.println("Login state in main.jsp: " + loginState);
%>
  <style>
      /* 팝업창 스타일 */
       .popup-overlay {
           position: fixed;
           top: 0;
           left: 0;
           width: 100%;
           height: 100%;
           display: none;
       }
       .popup-content {
           position: absolute;
           top: 20px;
           left: 20px;
           background: white;
           padding: 20px;
           width: 300px;
           border-radius: 8px;
           text-align: center;
       }
       .close-btn {
           cursor: pointer;
           color: red;
       }
  .carousel-inner > .item > img,
  .carousel-inner > .item > a > img {
    width: 70%;
    margin: auto;
  }
  .main-container{
    display: flex !important;
    justify-content: center; /* 가로 중앙 정렬 */
    align-items: center;     /* 세로 중앙 정렬 (필요할 경우) */
    width: 70%;
    height: 100%;            /* 부모 요소에 맞추기 위해 높이 설정 */
    margin: 0 auto !important; /* 상하 여백을 없애고 가로 중앙에 배치 */
	padding-bottom:60px;
  }

	.product-container-total {   /*상품 전체 컨테이너*/
        border: none;
        margin: 0 auto;
		width:70%;
		margin-bottom:50px; 
    }

	.product-container-title {   /*타이틀*/
		border-bottom:3px solid #be241c;
		font-family: "Noto Sans KR", sans-serif; /* 상세 설명 폰트 설정 */
        display: grid;
        grid-template-columns: repeat(auto-fill, minmax(250px, 1fr)); 
		font-size: 30px;
		padding-top:10px; 
		padding-left:25px;
		padding-bottom:8px;
		text-align:left;
    }
    
	.product-container-more {   /*더보기*/
		font-family: "Noto Sans KR", sans-serif; /* 상세 설명 폰트 설정 */
		font-size: 18px;
		color: #be241c;
		padding-top:15px;
		padding-bottom:20px;
		margin-right:0 auto;
    }
    
	.product-container-more a {
		color: #be241c;
		text-decoration: none; /* 밑줄 제거 */
	}
	
	.product-container-more a:hover {
	    color: #d9534f; /* 마우스 오버 시 색상 */
	}

      .product-container {   /*상품 배열 하는거*/
		padding-top:30px; 
        display: grid;
        grid-template-columns: repeat(auto-fill, minmax(250px, 1fr)); 
        gap: 30px; 
        max-width: 1200px; 
        margin: 0 auto;
    }
    
    .product {            /*상품 테두리*/
        border: 1px solid #ddd;
        padding: 16px;
        text-align: center;
        background-color: #f9f9f9; 
        border-radius: 8px; 
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1); 
        transition: transform 0.2s; 
    }
    
     .product:hover {
        transform: scale(1.02); /* 마우스 오버 시 크기 증가 */
    }
                                           
    .product img {       /*상품 이미지 크기*/
        width: 200px; 
        height: 200px;
        object-fit: cover;
    }

   .product-title {    
    font-family: "Noto Sans KR", sans-serif;
    font-weight: 300; /* 얇은 두께 설정 */
    font-size: 15px; 
    margin: 15px 0;
    text-align: left; /* 왼쪽 정렬 추가 */
}
    
    .product-price {     /* 가격 폰트 설정 */  
        font-family: "Noto Sans KR", sans-serif;
        font-size: 15px;
        color: #d32f2f;
        margin: 10px 0;
        text-align: left; /* 왼쪽 정렬 추가 */
    }
  
  </style>
  
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class="main-container">
  <br>
  <div id="myCarousel" class="carousel slide" data-ride="carousel">
    <!-- Indicators -->
    <ol class="carousel-indicators">
      <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
      <li data-target="#myCarousel" data-slide-to="1"></li>
    </ol>

    <!-- Wrapper for slides -->
    <div class="carousel-inner" role="listbox">

      <div class="item active">
      <a href="outfielder">
        <img src="./image/메인이미지.jpg" alt="글러브야수" style="height:100%; width:auto;">
		</a>
        <div class="carousel-caption">
        </div>
      </div>
      
      <div class="item">
      <a href="pitcherallround">
        <img src="./image/메인이미지.jpg" alt="글러브투수" style="height:100%; width:auto;">
        </a>
        <div class="carousel-caption">
        </div>
      </div>
  
    </div>

    <!-- Left and right controls -->
    <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
      <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
      <span class="sr-only">Previous</span>
    </a>
    <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
      <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
      <span class="sr-only">Next</span>
    </a>
  </div>
</div>

<div class="product-container-total">
<div class="product-container-title">Best</div>
<div class="product-container">
<c:forEach items="${list}" var="aa" varStatus="status" end="3">
    <div class="product">
        <a href="productdetail?itemnum=${aa.itemnum}">
            <img src="./image/${aa.image1}" alt="Product Image">
        </a>
        <div class="product-title">
             <a href="productdetail?itemnum=${aa.itemnum}" style="text-decoration: none; color: black;">${aa.product}</a>
        </div>

	<c:choose>
	    <c:when test="${aa.sale > 0}">
	        <div class="product-price">[${aa.sale}% 세일중] <fmt:formatNumber value="${aa.price}" type="number" groupingUsed="true"/>원 → <fmt:formatNumber value="${aa.saleprice}" type="number" groupingUsed="true"/>원</div>
	    </c:when>
	    <c:otherwise>
	        <div class="product-price"><fmt:formatNumber value="${aa.price}" type="number" groupingUsed="true"/>원</div>
	    </c:otherwise>
	</c:choose>
	
	<c:choose>
		<c:when test="${loginstate==true}">
			<div><a href="like_save?itemnum=${aa.itemnum}">👍 따봉</a></div>
		</c:when>
		
		<c:otherwise>
			<div><a href="login?redirect" onclick="showLoginAlert()">👍 따봉</a></div>
		</c:otherwise>
	</c:choose>

    </div>
</c:forEach>
</div>
<div class="product-container-more"><a href="bestitem">더보기</a></div>
</div>

<div class="product-container-total">
<div class="product-container-title">New</div>
<div class="product-container">
<c:forEach items="${list1}" var="aa1" varStatus="status" end="3">
    <div class="product">
        <a href="productdetail?itemnum=${aa1.itemnum}">
            <img src="./image/${aa1.image1}" alt="Product Image">
        </a>
        <div class="product-title">
             <a href="productdetail?itemnum=${aa1.itemnum}" style="text-decoration: none; color: black;">${aa1.product}</a>
        </div>
 
	<c:choose>
	    <c:when test="${aa1.sale > 0}">
	        <div class="product-price">[${aa1.sale}% 세일중] <fmt:formatNumber value="${aa1.price}" type="number" groupingUsed="true"/>원 → <fmt:formatNumber value="${aa1.saleprice}" type="number" groupingUsed="true"/>원</div>
	    </c:when>
	    <c:otherwise>
	        <div class="product-price"><fmt:formatNumber value="${aa1.price}" type="number" groupingUsed="true"/>원</div>
	    </c:otherwise>
	</c:choose>
	
	<c:choose>
		<c:when test="${loginstate==true}">
			<div><a href="like_save?itemnum=${aa1.itemnum}">👍 따봉</a></div>
		</c:when>
		
		<c:otherwise>
			<div><a href="login?redirect" onclick="showLoginAlert()">👍 따봉</a></div>
		</c:otherwise>
	</c:choose>

    </div>
</c:forEach>
</div>
<div class="product-container-more"><a href="newitem">더보기</a></div>
</div>

<div class="product-container-total">
<div class="product-container-title">Sale</div>
<div class="product-container">
<c:forEach items="${list2}" var="aa2" varStatus="status" end="3">
    <div class="product">
        <a href="productdetail?itemnum=${aa2.itemnum}">
            <img src="./image/${aa2.image1}" alt="Product Image">
        </a>
        <div class="product-title">
             <a href="productdetail?itemnum=${aa2.itemnum}" style="text-decoration: none; color: black;">${aa2.product}</a>
        </div>
 
	<c:choose>
	    <c:when test="${aa2.sale > 0}">
	        <div class="product-price">[${aa2.sale}% 세일중] <fmt:formatNumber value="${aa2.price}" type="number" groupingUsed="true"/>원 → <fmt:formatNumber value="${aa2.saleprice}" type="number" groupingUsed="true"/>원</div>
	    </c:when>
	    <c:otherwise>
	        <div class="product-price"><fmt:formatNumber value="${aa2.price}" type="number" groupingUsed="true"/>원</div>
	    </c:otherwise>
	</c:choose>
	
	<c:choose>
		<c:when test="${loginstate==true}">
			<div><a href="like_save?itemnum=${aa2.itemnum}">👍 따봉</a></div>
		</c:when>
		
		<c:otherwise>
			<div><a href="login?redirect" onclick="showLoginAlert()">👍 따봉</a></div>
		</c:otherwise>
	</c:choose>
	
    </div>
</c:forEach>
</div>
<div class="product-container-more"><a href="saleitem">더보기</a></div>
</div>
</body>
</html>
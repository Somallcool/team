<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
th, td{
text-align: center
}
</style>
<meta charset="UTF-8">
<title>
</title>
</head>
<body>
<div class="container">
<aside class="sidebar">
	<h2>커뮤니티</h2>
		<ul>
			<li><a href="gongjiboard">공지사항</a></li>
			<li><a href="eventboard">이벤트</a></li>
			<li><a href="board">자유게시판</a></li>
			<li><a href="sosickboard">야구소식</a></li>
			<li><a href="iljung">경기일정</a></li>
		</ul>
	<div class="kborank">
		<h3>2024시즌 KBO순위</h3>
		<table style="width:310px">
			<tr>
				<th>순위</th><th>팀</th><th>승</th><th>패</th><th>무</th><th>승률</th>
			</tr>
			<tr>
				<td>1</td><td>기아</td><td>87</td><td>55</td><td>2</td><td>0.613</td>
			</tr>
			<tr>
				<td>2</td><td>삼성</td><td>78</td><td>64</td><td>2</td><td>0.549</td>
			</tr>
			<tr>
				<td>3</td><td>LG</td><td>76</td><td>66</td><td>2</td><td>0.535</td>
			</tr>
			<tr>
				<td>4</td><td>두산</td><td>74</td><td>68</td><td>2</td><td>0.521</td>
			</tr>
			<tr>
				<td>5</td><td>KT</td><td>72</td><td>70</td><td>2</td><td>0.507</td>
			</tr>
			<tr>
				<td>6</td><td>SSG</td><td>72</td><td>70</td><td>2</td><td>0.507</td>
			</tr>
			<tr>
				<td>7</td><td>롯데</td><td>66</td><td>74</td><td>4</td><td>0.471</td>
			</tr>
			<tr>
				<td>8</td><td>한화</td><td>66</td><td>76</td><td>2</td><td>0.465</td>
			</tr>
			<tr>
				<td>9</td><td>NC</td><td>61</td><td>81</td><td>2</td><td>0.430</td>
			</tr>
			<tr>
				<td>10</td><td>키움</td><td>58</td><td>86</td><td>0</td><td>0.403</td>
			</tr>
		</table>
	</div>
</aside>
<div class="maindata">
<table border="1" width="800px" align="center">
<caption><h2>야구소식</h2></caption>
<tr style="border-bottom: none;" height="35px">
<c:choose>
	<c:when test="${empty list}">
		<th colspan="5" style="text-align: right;">
			최신 <a href="sosickdayup" style="margin-right: 3px;">↑ </a><a href="sosickdaydown" style="margin-right: 5px;">↓ </a> 
			조회수 <a href="sosickviewup" style="margin-right: 3px;">↑ </a><a href="sosickviewdown" style="margin-right: 5px;">↓ </a>
		</th>
	</c:when>
	<c:otherwise>
		<td colspan="2" style="text-align: left; padding-left: 10px;">"${svalue}"로 검색한 결과</td>
		<th colspan="3" style="text-align: right;">
			최신 <a href="sosickdayup" style="margin-right: 3px;">↑ </a><a href="sosickdaydown" style="margin-right: 5px;">↓ </a> 
			조회수 <a href="sosickviewup" style="margin-right: 3px;">↑ </a><a href="sosickviewdown" style="margin-right: 5px;">↓ </a>
		</th>
	</c:otherwise>
</c:choose>
</tr>
	
	<tr height="35px">
		<th style="border-right:none;" width="50px">번호</th>
		<th style="border-right:none; border-left:none;" width="500px">제목</th>
		<th style="border-right:none; border-left:none;" width="100px">작성자</th>
		<th style="border-right:none; border-left:none;" width="100px">작성일</th>
		<th style="border-left:none;" width="50px">조회수</th>
	</tr>

<c:choose>
    <c:when test="${empty list}">
		<tr height="35px">
			<td colspan="6">"${svalue}"로 검색한 결과가 없습니다.</td>
		</tr>
	</c:when>
	<c:otherwise>	
		<c:forEach items="${list}" var="sosick">
			<tr height="35px">
				<td style="border-right:none;">${sosick.snum}</td>
				<td style="border-right:none; border-left:none; text-align: left; padding-left: 5px;"><a href="sosickdetail?snum=${sosick.snum}">[${sosick.stag}] ${sosick.stitle}</a></td>
				<td style="border-right:none; border-left:none;">${sosick.nickname}</td>
				<td style="border-right:none; border-left:none;">${sosick.sdate}</td>
				<td style="border-left:none;">${sosick.scnt}</td>
			</tr>
		</c:forEach>
	</c:otherwise>
</c:choose>

<!-- 페이징처리 4444444444-->
<tr style="border-bottom:none;" height="25px">
   <td colspan="8" style="text-align: center; border-bottom:none;">
   
   <c:if test="${paging.startPage!=1 }">
      <a href="sosicksearchview?nowPage=${paging.startPage-1 }&cntPerPage=${paging.cntPerPage}"></a>
   </c:if>   
      <c:forEach begin="${paging.startPage }" end="${paging.endPage}" var="p"> 
         <c:choose>
            <c:when test="${p == paging.nowPage }">
               <b><span style="color: red;">${p}</span></b>
            </c:when>   
            <c:when test="${p != paging.nowPage }">
               <a href="sosicksearchview?nowPage=${p}&cntPerPage=${paging.cntPerPage}">${p}</a>
            </c:when>   
         </c:choose>
      </c:forEach>
      
      <c:if test="${paging.endPage != paging.lastPage}">
      <a href="sosicksearchview?nowPage=${paging.endPage+1}&cntPerPage=${paging.cntPerPage }">  </a>
   </c:if>
   
   </td>
</tr>
<!-- 페이징처리 4444444444-->
	<tr style="border-top:none;" height="35px">
		<td colspan="7" align="left">
			<form action="sosicksearchsave" method="post">
				<select name="sosickkey" >
					<option value="stitle">제목
					<option value="scontents">내용
					<option value="nickname">글쓴이
				</select>
				<input type="text" name="svalue" style="width: 250px" placeholder="검색어를 입력해주세요.">
				<input type="submit" value="찾기">
		<c:choose>
			<c:when test="${adminloginstate==true}">
				<input type="button" onclick="location.href='sosickinput'" value="글쓰기">
				<input type="button" onclick="location.href='sosickboard'" value="목록">
			</c:when>
			<c:otherwise>
				<input type="button" onclick="location.href='sosickboard'" value="목록">
			</c:otherwise>
		</c:choose>
			</form>
		</td>
	</tr>
</table>
</div>
</div>
</body>
</html>
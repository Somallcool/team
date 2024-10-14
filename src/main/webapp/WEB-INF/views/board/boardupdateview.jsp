<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<form action="boardupdate" method="post" enctype="multipart/form-data">
<table border="1" align="center" width="800px">
<caption><h3>자유게시판</h3></caption>
<input type="hidden" name="nickname" value="${list.nickname}" readonly>
<input type="hidden" name="cnum" value="${list.cnum}" readonly>
	<tr>
		<th>태그</th>
		<td style="text-align: left;">
			<select name="tag">
				<option value="국내야구">국내야구
				<option value="해외야구">해외야구
				<option value="유머">유머
				<option value="군사">군사
				<option value="사회">사회
				<option value="경제">경제
				<option value="기타">기타
			</select>
		</td>
	</tr>
	<tr>
		<th>제목</th>
		<td style="text-align: left;">
			<input type="text" name="title" style="width:725px" value="${list.title}" required="required">
		</td>
	</tr>
	<tr>
		<th>내용</th>
		<td style="text-align: left;">
			<textarea rows="20" cols="100px" name="ccontents" required="required">${list.ccontents}</textarea>
		</td>
	</tr>
		<tr>
		<th>파일첨부</th>
		<td>
			<input type="file" name="cimage" required="required">
		</td>
	</tr>
	<tr>
	<td colspan="2">
	<input type="submit" value="수정">
	<input type="reset" value="수정취소">
	<input type="button" onclick="location.href='board'" value="목록">
	</td>
	</tr>
</table>
</form>
</div></div>
</body>
</html>
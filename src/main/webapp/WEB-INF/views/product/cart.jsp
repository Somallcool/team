<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>장바구니</title>
<script type="text/javascript">

  function validateSelection(action) {
    var checkboxes = document.getElementsByName('selectedItems');
    var selected = false;

    for (var i = 0; i < checkboxes.length; i++) {
      if (checkboxes[i].checked) {
        selected = true;
        break;
      }
    }

    if (!selected) {
      alert("상품을 선택하세요.");
      return false;  
    }
    
    if(action === 'delete'){
      var selectedItems = [];
      for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].checked) {
          selectedItems.push(checkboxes[i].value);
        }
      }

      if(selectedItems.length > 0){
        $.ajax({
          type: 'post',
          url: 'deleteitems',
          contentType: 'application/json',
          data: JSON.stringify({ items: selectedItems }),  
          async: true,  
          success: function(response) {
            alert("선택한 상품이 삭제되었습니다.");
            location.reload();  
          },
          error: function(xhr, status, error) {
            alert("상품 삭제에 실패했습니다.");
          }
        });
      }
      return false;  
    }
    
    var form = document.getElementById("cartForm");
    form.action = action === 'buy' ? 'buy' : 'delete';
    return true;
  }
</script>
</head>
<body>
  <h1>장바구니</h1>

  <form id="cartForm" method="post" action="#">
    <input type="hidden" id="loginState" value="${loginstate}"> 
    <c:choose>
        <c:when test="${empty items}">
            <p>장바구니에 상품이 없습니다.</p>
        </c:when>
        <c:otherwise>
            <table>
                <thead>
                    <tr>
                        <th>상품명</th>
                        <th>상품이미지</th>
                        <th>옵션</th>
                        <th>수량</th>
                        <th>가격</th>
                        <th>총 가격</th>
                        <th>선택</th> 
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${items}">
                        <tr>
                            <td>${item.product}</td>
                            <td><img src="./image/${item.image1}" alt="${item.image1}" width="100"></td>
                            <td>${item.op1}</td>
                            <td>${item.count}</td>
                            <td>${item.price}원</td>
                            <td>${item.price * item.count}원</td>

                            <td><input type="checkbox" name="selectedItems" value="${item.itemnum}"></td> 
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <button type="submit" onclick="return validateSelection('buy');">선택한 상품 구매</button>
            <button type="submit" onclick="return validateSelection('delete');">선택한 상품 삭제</button>
        </c:otherwise>
    </c:choose>
  </form>
</body>
</html>
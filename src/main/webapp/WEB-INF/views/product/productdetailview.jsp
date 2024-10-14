<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Product Detail Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }
        table {
            margin: 50px auto;
            width: 90%;
            max-width: 1200px;
            border-collapse: collapse;
        }
        td, th {
            padding: 10px;
            border: 1px solid #ddd;
        }
        .main-image {
            width: 500px;
            height: 550px;
        }
        .product-title {
            font-size: 18px;
            font-weight: bold;
        }
        .price {
            color: black;
            font-size: 20px;
        }
        .option-title {
            font-size: 16px;
            font-weight: bold;
            margin-top: 20px;
            margin-left: 5px;
        }
        .responsive-image {
            width: 100%;
            height: auto;
            max-width: 1000px;
        }
    </style>
    
    <script>
        function updateTotal() {
            const price = parseFloat("${dto.price}");
            const count = document.getElementById("count").value;
            const total = price * count;
            document.getElementById("tot").innerText = "합계: " + total + "원";
        }
        
        function addToCart() {
            // 여기서는 실제로 폼을 제출하는 방식으로 수정합니다.
            document.getElementById("productForm").submit(); 
        }
    </script>  
</head>
<body>
    <form id="productForm" action="/team/cart" method="get" enctype="multipart/form-data">
        <input type="hidden" name="itemnum" id="itemnum" value="${dto.itemnum}">
        <input type="hidden" name="price" id="price" value="${dto.price}">
        <input type="hidden" name="product" id="product" value="${dto.product}">
        <input type="hidden" name="op1" id="op1">
       
        <table>
            <tr>
                <td rowspan="6">
                    <img src="./image/${dto.image1}" class="main-image" alt="Main Image">
                </td>
                <td colspan="6" class="product-title">${dto.product}</td>
            </tr>
            <tr>
                <td colspan="6" class="price">판매가 ${dto.price}원</td>
            </tr>
            
            
            <c:choose>
                <c:when test="${dto.cg_code == 'fcg002'}">
                    <tr id="batOptions" style="display:block;">
                        <td colspan="6">
                            <div class="option-title">배트 사이즈</div>
                            <select name="op1" id="op1" onchange="this.options[this.selectedIndex].value = this.value;">
                                <option value="">[필수] 옵션을 선택해 주세요</option>
                                <option value="33인치/28온스">33인치/28온스</option>
                                <option value="32인치/27온스">32인치/27온스</option>
                            </select>
                        </td>
                    </tr>
                </c:when>
                <c:when test="${dto.cg_code == 'fcg001'}">
                    <tr id="gloveOptions" style="display:block;">
                        <td colspan="6">
                            <div class="option-title">글러브 선택</div>
                            <select name="op1" id="op1" onchange="this.options[this.selectedIndex].value = this.value;">
                                <option value="">[필수] 옵션을 선택해 주세요</option>
                                <option value="우투(왼손착용)">우투(왼손착용)</option>
                                <option value="좌투(오른손착용)">좌투(오른손착용)</option>
                            </select>
                        </td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td colspan="6">선택할 수 있는 옵션이 없습니다.</td>
                    </tr>
                </c:otherwise>
            </c:choose>
           
            <tr>
                <td colspan="6">  
                    <label for="count">수량:</label>
                    <input type="number" id="count" name="count" min="1" value="1" onchange="updateTotal()" oninput="updateTotal()">
                </td>
            </tr>
            
            
            <tr>
                <td colspan="6" id="tot">합계: ${dto.price}원</td>
            </tr>
            
            
            <tr>
                <td colspan="6">
                    <button type="button" onclick="addToCart()">장바구니</button>
                    <a href="">바로구매</a>
                </td>
            </tr>
           
           
            <tr>
                <td colspan="4" align="center">
                    <img src="./image/${dto.dimage}" class="responsive-image" alt="Detailed Image">
                </td>
            </tr>
        </table>
    </form>
</body>
</html>

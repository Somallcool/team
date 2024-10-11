package com.mbc.team.cart;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CartController {
	@Autowired
	CartService cartService;
	
	@RequestMapping(value = "/cart")
	public String cart(HttpServletRequest request, HttpSession hs) {
		try {
	        int itemnum = Integer.parseInt(request.getParameter("itemnum"));
	        String priceStr = request.getParameter("price");
	        String countStr = request.getParameter("count");
	        String product = request.getParameter("product");
	        String op1 = request.getParameter("op1");

	        // �� ���ڿ� üũ �� �⺻�� ����
	        if (priceStr == null || priceStr.isEmpty()) {
	            throw new NumberFormatException("price�� ������� �� �����ϴ�.");
	        }
	        if (countStr == null || countStr.isEmpty()) {
	            throw new NumberFormatException("count�� ������� �� �����ϴ�.");
	        }

	        int price = Integer.parseInt(priceStr);
	        int count = Integer.parseInt(countStr);

	        // CartItem ��ü ���� (������ �°� ���� �ʿ�)
	        CartItem item = new CartItem(itemnum, product, price, count);

	        // ���ǿ��� īƮ ��������
	        List<CartItem> cart = (List<CartItem>) hs.getAttribute("cart");
	        if (cart == null) {
	            cart = new ArrayList<>();
	        }

	        // īƮ�� �߰�
	        cart.add(item);
	        hs.setAttribute("cart", cart); // ���ǿ� īƮ ����

	        return "cart"; // cart �������� �̵�
	    } catch (NumberFormatException e) {
	        // ���� ó�� ���� �߰� (��: ���� �������� �����̷�Ʈ)
	        System.err.println("�߸��� �Է� ��: " + e.getMessage());
	        return "error"; // ���� �������� �����̷�Ʈ
	    }
	
}
}

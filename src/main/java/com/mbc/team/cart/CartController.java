package com.mbc.team.cart;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mbc.team.login.LoginDTO;

@Controller
public class CartController {

	@Autowired
	SqlSession sqlSession;

	@RequestMapping(value = "/insertcart")
	public String cart(HttpServletRequest request, HttpSession hs) {

		String itemnumStr = request.getParameter("itemnum");
		String priceStr = request.getParameter("price");
		String countStr = request.getParameter("count");
		String product = request.getParameter("product");
		String op1 = request.getParameter("op1");
		String image1 = request.getParameter("image1");
		String id = request.getParameter("id");

		int itemnum = Integer.parseInt(itemnumStr);
		int price = Integer.parseInt(priceStr);
		int count = Integer.parseInt(countStr);

		
		CartItem cartItem = new CartItem();
		cartItem.setItemnum(itemnum);
		cartItem.setId(id);
		cartItem.setProduct(product);
		cartItem.setPrice(price);
		cartItem.setCount(count);
		cartItem.setImage1(image1);
		cartItem.setOp1(op1);

		CartService cs = sqlSession.getMapper(CartService.class);
		cs.insert(cartItem);

		return "redirect:/productdetail?itemnum=" + itemnum;

	}

	@RequestMapping(value = "/cart")
	public String cart1(HttpServletRequest request, HttpSession hs, Model mo, HttpServletResponse response)
			throws IOException {

		hs = request.getSession();
		Boolean loginState = (Boolean) hs.getAttribute("loginstate");

		if (loginState != null && loginState) {

			LoginDTO dto3 = (LoginDTO) hs.getAttribute("dto3");
			String id = dto3.getId();

			CartService cs = sqlSession.getMapper(CartService.class);
			List<CartItem> items = cs.selectitem(id);
			mo.addAttribute("items", items);

			return "cart";
		} else {

			response.setContentType("text/html;charset=utf-8");
			PrintWriter pww = response.getWriter();
			pww.print("<script> alert('로그인 후 이용해주세요.');</script>");
			pww.print("<script> location.href='/team/login';</script>");
			pww.flush();
			return null;
		}

	}

	@ResponseBody
	@RequestMapping(value = "/deleteitems", method = RequestMethod.POST)
	public String deleteSelectedItems(@RequestBody Map<String, List<String>> param, HttpSession hs) {
		List<String> items = param.get("items");

		LoginDTO dto3 = (LoginDTO) hs.getAttribute("dto3");

		if (dto3 == null) {
			return "로그인 후 이용해주세요.";
		}
		System.out.println("아이템 : " + items);
		System.out.println("아이디 : " + dto3.getId());
		CartService cs = sqlSession.getMapper(CartService.class);
		cs.deleteSelectItems(dto3.getId(), items);

		return "success";
	}

	@RequestMapping(value = "/buydirectitem", method = RequestMethod.POST)
	public String buydirectitem(HttpSession hs, Model mo, HttpServletRequest request) {
		int itemnum=Integer.parseInt(request.getParameter("itemnum"));
		int count=Integer.parseInt(request.getParameter("count"));
		int price=Integer.parseInt(request.getParameter("price"));
		String op1=request.getParameter("op1");
		String product=request.getParameter("product");
		String image1=request.getParameter("image1");
		
		
		System.out.println("ItemNum: " + itemnum);
		System.out.println("Price: " + price);
		System.out.println("Product: " + product);
		System.out.println("Option: " + op1);
		System.out.println("Count: " + count);

		LoginDTO dto3 = (LoginDTO) hs.getAttribute("dto3");
		String id = dto3.getId();

		mo.addAttribute("itemnum", itemnum);
		mo.addAttribute("product", product);
		mo.addAttribute("count", count);
		mo.addAttribute("op1", op1);
		mo.addAttribute("price", price);

		return "buyproduct";
	}

	@RequestMapping("/buy")
	public String buySelectedItems(@RequestParam("selectedItems") List<String> selectedItems, HttpSession hs,
			Model mo, HttpServletRequest request) {

		LoginDTO dto3 = (LoginDTO) hs.getAttribute("dto3");
		String id = dto3.getId();
		System.out.println("아이디 : " + id);
		CartService cs = sqlSession.getMapper(CartService.class);
		List<CartItem> cart = cs.getCartItems(id);

		if (cart == null || selectedItems == null || selectedItems.isEmpty()) {
			mo.addAttribute("errorMessage", "선택한 상품이 없습니다.");
			return "errorPage";
		}

		List<CartItem> purchasedItems = new ArrayList<>();
		int totalPrice = 0;

		for (CartItem item : cart) {
			if (selectedItems.contains(String.valueOf(item.getItemnum()))) {
				purchasedItems.add(item);
				totalPrice += item.getPrice() * item.getCount();
				System.out.println("ID: " + id + ", ItemNum: " + item.getItemnum());
				cs.deleteCartItem(id, item.getItemnum());
			}
		}

		mo.addAttribute("purchasedItems", purchasedItems);
		mo.addAttribute("totalPrice", totalPrice);

		return "purchaseConfirmation"; 
	}

}

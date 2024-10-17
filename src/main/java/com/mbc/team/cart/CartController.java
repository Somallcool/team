package com.mbc.team.cart;



import java.io.IOException;
import java.io.PrintWriter;
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
        
        // CartItem ��ü ���� �� �� ����
        CartItem cartItem = new CartItem();
        cartItem.setItemnum(itemnum);
        cartItem.setId(id);
        cartItem.setProduct(product);
        cartItem.setPrice(price);
        cartItem.setCount(count);
        cartItem.setImage1(image1);
        cartItem.setOp1(op1);

        // CartService ȣ���Ͽ� CartItem ��ü ����
        CartService cs = sqlSession.getMapper(CartService.class);
        System.out.println("Inserting Cart Item: " + cartItem);
        cs.insert(cartItem);  // CartItem ��ü ����
        
        
        
        return "redirect:/product";  // ��ٱ��� �������� �̵�
        
    }
    
    @RequestMapping(value = "/cart")
    public String cart1(HttpServletRequest request, HttpSession hs, Model mo,HttpServletResponse response) throws IOException {
    	
    	hs= request.getSession();
	    Boolean loginState = (Boolean) hs.getAttribute("loginstate");
    	
	    if (loginState != null && loginState) {
	    	
	   
    	LoginDTO dto3=(LoginDTO) hs.getAttribute("dto3");
    	String id =dto3.getId();
    	
    	CartService cs = sqlSession.getMapper(CartService.class);
    	List<CartItem> items=cs.selectitem(id);
    	mo.addAttribute("items",items);
    	
    	
    	return "cart";
	    }
	    else {
	        // �α��� ���°� �ƴ� ���
	        response.setContentType("text/html;charset=utf-8");
	        PrintWriter pww = response.getWriter();
	        pww.print("<script> alert('�α��� �� �̿����ּ���.');</script>");
	        pww.print("<script> location.href='/team/login';</script>");
	        pww.flush();
	        return null;
	    }
	    	
    }
    @ResponseBody
    @RequestMapping(value = "/deleteitems", method = RequestMethod.POST)
    public String deleteSelectedItems(@RequestBody Map<String, List<String>> param, HttpSession hs) {
        List<String> items = param.get("items");  // JSON���� "items"�� ����

        LoginDTO dto3 = (LoginDTO) hs.getAttribute("dto3");

        if (dto3 == null) {
            return "�α��� �� �̿����ּ���."; 
        }
        System.out.println("������ : "+items);
        System.out.println("���̵� : "+dto3.getId());
        CartService cs = sqlSession.getMapper(CartService.class);
        cs.deleteSelectItems(dto3.getId(), items);  // id�� items�� ���۷� ����
        
        return "success";
    }
    
    
    
    
    
    

 
    
}

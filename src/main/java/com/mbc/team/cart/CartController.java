package com.mbc.team.cart;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CartController {
    @Autowired
    CartService cartService;

    @RequestMapping(value = "/cart")
    public String cart(HttpServletRequest request, HttpSession hs) {
        try {
            String itemnumStr = request.getParameter("itemnum");
            String priceStr = request.getParameter("price");
            String countStr = request.getParameter("count");
            String product = request.getParameter("product");
            String op1 = request.getParameter("op1");
            String image1 = request.getParameter("image1");

            // �� ���ڿ� üũ �� �⺻�� ����
            if (itemnumStr != null && !itemnumStr.isEmpty() &&
                priceStr != null && !priceStr.isEmpty() &&
                countStr != null && !countStr.isEmpty() &&
                product != null && !product.isEmpty() &&
                image1 != null && !image1.isEmpty()) {

                int itemnum = Integer.parseInt(itemnumStr);
                int price = Integer.parseInt(priceStr);
                int count = Integer.parseInt(countStr);

                // CartItem ��ü ����
                CartItem newItem = new CartItem(itemnum, product, price, op1, count, image1);

                // ���ǿ��� īƮ ��������
                List<CartItem> cart = (List<CartItem>) hs.getAttribute("cart");
                if (cart == null) {
                    cart = new ArrayList<>();
                }

                // ������ ���� �������� �����ϴ��� Ȯ��
                boolean itemExists = false;
                for (CartItem item : cart) {
                    if (item.getItemnum() == newItem.getItemnum() && item.getOp1().equals(newItem.getOp1())) {
                        // �������� �̹� �����ϸ� ������ ������Ų��
                        item.setCount(item.getCount() + newItem.getCount());
                        itemExists = true;
                        break;
                    }
                }

                // ���ο� �������� ������ �߰�
                if (!itemExists) {
                    cart.add(newItem);
                }

                // ���ǿ� īƮ ����
                hs.setAttribute("cart", cart);
                System.out.println("���� op1 ��: " + op1);
            }

            return "cart"; // cart �������� �̵�

        } catch (NumberFormatException e) {
            // ���� ó�� ���� �߰�
            System.err.println("�߸��� �Է� ��: " + e.getMessage());
            request.setAttribute("errorMessage", "�Է� ���� �߸��Ǿ����ϴ�. �ٽ� �õ��� �ּ���.");
            return "error"; // ���� �������� �����̷�Ʈ
        } catch (Exception e) {
            // ��Ÿ ���� ó��
            System.err.println("ó�� �� ���� �߻�: " + e.getMessage());
            request.setAttribute("errorMessage", "�� �� ���� ������ �߻��߽��ϴ�. �ٽ� �õ��� �ּ���.");
            return "error"; // ���� �������� �����̷�Ʈ
        }
    }

    @RequestMapping("/deleteCartItem")
    public String deleteCartItem(@RequestParam("itemnum") int itemnum, HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart != null) {
            cart.removeIf(item -> item.getItemnum() == itemnum);
            session.setAttribute("cart", cart);
        }
        return "redirect:/cart"; // ��ٱ��� �������� �����̷�Ʈ
    }

    @RequestMapping("/clearCart")
    public String clearCart(HttpSession session) {
        session.removeAttribute("cart"); // ���ǿ��� ��ٱ��� ����
        return "redirect:/cart"; // ��ٱ��� �������� �����̷�Ʈ
    }

 // �α��� ���� üũ �� ���� ó��
    @RequestMapping(value = "/buySelectedItems", method = RequestMethod.POST)
    public String buySelectedItems(@RequestParam(value = "selectedItems", required = false) List<String> selectedItems,
                                    HttpSession session, HttpServletRequest request) {
        // �α��� ���°� false�� ��� �α��� �������� ���𷺼�
        Boolean loginState = (Boolean) session.getAttribute("loginstate");
        if (loginState == null || !loginState) {
            return "redirect:/login"; // �α��ε��� �ʾ����� �α��� �������� ���𷺼�
        }

        // ���õ� �������� ���� ��쿡�� ó��
        if (selectedItems != null && !selectedItems.isEmpty()) {
            List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
            if (cart != null) {
                List<CartItem> itemsToBuy = new ArrayList<>();
                // ���õ� �����۸� ���͸��Ͽ� itemsToBuy ����Ʈ�� �߰�
                for (CartItem item : cart) {
                    if (selectedItems.contains(String.valueOf(item.getItemnum()))) {
                        itemsToBuy.add(item);
                    }
                }
                // �������� ���� ó���ϴ� ������ �߰� (��: DB�� ����, ���� ��)
                session.setAttribute("itemsToBuy", itemsToBuy);  // ���ǿ� ���õ� ������ ����
            }
        } else {
            // ���õ� ��ǰ�� ������ ��ٱ��� �������� �����̷�Ʈ
            request.setAttribute("errorMessage", "��ǰ�� �����ϼ���.");
            return "cart";  // ��ٱ��� �������� �����̷�Ʈ
        }
        return "buyproduct";  // ���� �������� �̵�
    }

    // ���� ó�� ���� (�α��� ���� Ȯ�� �� ����)
    @RequestMapping(value = "/confirmPurchase", method = RequestMethod.POST)
    public String confirmPurchase(HttpSession session) {
        // �α��� ���°� false�� ��� �α��� �������� ���𷺼�
        Boolean loginState = (Boolean) session.getAttribute("loginstate");
        if (loginState == null || !loginState) {
            return "redirect:/login"; // �α��ε��� �ʾ����� �α��� �������� ���𷺼�
        }

        // ��ٱ��Ͽ��� ������ ��ǰ ��������
        List<CartItem> itemsToBuy = (List<CartItem>) session.getAttribute("itemsToBuy");

        if (itemsToBuy != null && !itemsToBuy.isEmpty()) {
            // ���⿡ DB�� �����ϴ� ���� (��: �ֹ� ���� ����)
            // ���÷� DB�� �����Ѵٰ� ����
            for (CartItem item : itemsToBuy) {
                // DB�� �����ϴ� ���� �߰� (��: �ֹ� ���̺� ����)
                System.out.println("������ ��ǰ: " + item.getProduct() + " ����: " + item.getCount());
            }

            // ���� �Ϸ� �� ��ٱ��Ͽ��� �ش� ��ǰ ����
            List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
            if (cart != null) {
                // ������ ��ǰ�鸸 ����
                cart.removeIf(item -> itemsToBuy.contains(item));
                session.setAttribute("cart", cart);  // ������ ��ٱ��� �ٽ� ���ǿ� ����
            }

            // ���� �Ϸ� �������� ���𷺼�
            return "purchaseComplete";  // ���� �Ϸ� �������� �̵�
        } else {
            // ��ǰ�� ������ ��ٱ��Ϸ� ���ư���
            return "redirect:/cart";  // ��ٱ��Ϸ� ���𷺼� ����
        }
    }
}
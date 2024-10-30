package com.mbc.team.FAQ;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mbc.team.member.MemberDTO;

@Controller
public class FAQController {

	/*
	 * �˻� Ű���� [�ۼ�, ����, �Խ���, ����, ��������, Update, Delete, �˻�, ���]
	 */
	
	@Autowired
	SqlSession sqlSession;
	HttpSession hs;
	FAQService fs;
	FAQadminService fs2;
	
	// ��� �����ؾ��մϴ�.
	String path = "C:\\Users\\3-16\\git\\team\\src\\main\\webapp\\image";

	// 1:1 ���Ǳ� �ۼ� (ȸ���� �� �ۼ� ����)
	@RequestMapping(value = "/faqin")
	public String faq0member(HttpServletResponse response, HttpServletRequest request, Model mo) throws IOException {
		
		hs = request.getSession();
		Boolean FAQinput = (Boolean) hs.getAttribute("loginstate");
		
		if (FAQinput == null || !FAQinput) {
			
			response.setContentType("text/html;charset=utf-8");
			PrintWriter pww=response.getWriter();
			pww.print("<script> alert('�α��� �� �̿����ֻ���')</script>");
			pww.print("<script> location.href='login'</script>");
			pww.close();
			return "redirect:/login";
		}
		else 
		{
			return "faqinput";
		}
	}
	
	// 1:1 ���Ǳ� ����(�̹��� max 3����� ����) +@ ���� �� �ٷ� �������� �̵� O
	@RequestMapping(value = "/faqsave", method = RequestMethod.POST)
	public String faq1member(MultipartHttpServletRequest mul, HttpSession hs) throws IllegalStateException, IOException {
	    
		String nickname = mul.getParameter("nickname");
		String tab = mul.getParameter("tab");
	    String title = mul.getParameter("title");
	    String fcontents = mul.getParameter("fcontents");
	    String openclose = mul.getParameter("openclose");

	    // ���� ó�� (������ ���� ��� �� ���ڿ��� ó��)
	    MultipartFile fimg1 = mul.getFile("fimage1");
	    MultipartFile fimg2 = mul.getFile("fimage2");
	    MultipartFile fimg3 = mul.getFile("fimage3");

	    String fname1 = (fimg1 != null && !fimg1.isEmpty()) ? fimg1.getOriginalFilename() : "";
	    String fname2 = (fimg2 != null && !fimg2.isEmpty()) ? fimg2.getOriginalFilename() : "";
	    String fname3 = (fimg3 != null && !fimg3.isEmpty()) ? fimg3.getOriginalFilename() : "";

	    // ���� ���� (������ ���� ��쿡�� ����)
	    if (!fname1.isEmpty()) {
	        fimg1.transferTo(new File(path + "\\" + fname1));
	    }
	    if (!fname2.isEmpty()) {
	        fimg2.transferTo(new File(path + "\\" + fname2));
	    }
	    if (!fname3.isEmpty()) {
	        fimg3.transferTo(new File(path + "\\" + fname3));
	    }

	    // FAQ ������ ����
	    fs = sqlSession.getMapper(FAQService.class);
	    fs.faqinsert(tab, title, fcontents, nickname, fname1, fname2, fname3, openclose);
	    int cnum = fs.save_detail();

	    return "redirect:/faqdetail?cnum="+cnum;
	}

	// 1:1 ���Ǳ� �Խ���(����¡) +@ ����, ����� ���� O
	@RequestMapping(value = "/faqout")
	public String faq2(HttpServletRequest request, PageDTO dto, Model mo) {
		String tab = request.getParameter("tab");
		String nowPage = request.getParameter("nowPage");
		String cntPerPage = request.getParameter("cntPerPage");

		fs = sqlSession.getMapper(FAQService.class);
		ArrayList<FAQDTO> list = fs.faqboard();
		
		mo.addAttribute("list", list);

		int total = fs.total(tab);
		if (nowPage == null && cntPerPage == null) {
			nowPage = "1";
			cntPerPage = "10";
		} else if (nowPage == null) {
			nowPage = "1";
		} else if (cntPerPage == null) {
			cntPerPage = "10";
		}
		dto = new PageDTO(total, Integer.parseInt(nowPage), Integer.parseInt(cntPerPage));
		mo.addAttribute("paging", dto);
		mo.addAttribute("list", fs.page(dto));

		return "faqoutput";
	}

	// 1:1 ���Ǳ� ���ı���(��ȸ��������, �ֽż�) +@ ����¡ �ʿ�
	@RequestMapping(value = "/category")
	public String faq3(HttpServletRequest request, PageDTO dto, Model mo) {
		
		String faq_category = request.getParameter("faq_category");
		String tab = request.getParameter("tab");
		String nowPage = request.getParameter("nowPage");
		String cntPerPage = request.getParameter("cntPerPage");

		fs = sqlSession.getMapper(FAQService.class);
		ArrayList<FAQDTO> list = null;
		if (faq_category.equals("faqcnt")) {
			list = fs.category1();
		} else {
			list = fs.category2();
		}
		mo.addAttribute("list", list);
		
		int total = fs.total(tab);
		if (nowPage == null && cntPerPage == null) {
			nowPage = "1";
			cntPerPage = "10";
		} else if (nowPage == null) {
			nowPage = "1";
		} else if (cntPerPage == null) {
			cntPerPage = "10";
		}
		dto = new PageDTO(total, Integer.parseInt(nowPage), Integer.parseInt(cntPerPage));
		mo.addAttribute("paging", dto);
		mo.addAttribute("list", fs.page(dto));
		
		return "faqoutput";
	}

	// 1:1 ���Ǳ� �亯 �ۼ�(�����ڸ� �亯 �ۼ�)
	@RequestMapping(value = "/faqreply", method = RequestMethod.POST)
	public String faq4admin(HttpServletResponse response, HttpServletRequest request, Model mo) throws IOException {
		
		hs = request.getSession();
		boolean login = (Boolean)hs.getAttribute("adminloginstate");
		
		if(login)
		{
			int cnum = Integer.parseInt(request.getParameter("cnum"));
			fs = sqlSession.getMapper(FAQService.class);
			ArrayList<FAQDTO> list = fs.faqreply(cnum);
			mo.addAttribute("faqlist", list);
	
			return "faqreplyview";
		}
			return "redirect:/main";
		
	}

	// 1:1 ���Ǳ� �亯 ����(������ �亯�� 1���� ����) +@ ���������� �ٷ� �̵� ��� O
	@RequestMapping(value = "/faqreplysave", method = RequestMethod.POST)
	public String faq5admin(HttpServletRequest request) throws IllegalStateException, IOException {
		int cnum = Integer.parseInt(request.getParameter("cnum"));
		String tab = request.getParameter("tab");
		String title = request.getParameter("title");
		String fcontents = request.getParameter("fcontents");
		String nickname = request.getParameter("nickname");
		int groups = Integer.parseInt(request.getParameter("groups"));
		int step = Integer.parseInt(request.getParameter("step"));
		int indent = Integer.parseInt(request.getParameter("indent"));

		fs = sqlSession.getMapper(FAQService.class);
		fs.faqstepup(groups, step);
		step++;
		indent++;
		;
		fs.faqreplysave(cnum, tab, title, fcontents, nickname, groups, step, indent);

		return "redirect:/faqdetail?cnum=" + cnum;
	}

	// 1:1 ���Ǳ� ��������(�������� ��� + ������ �亯 ���� ��� +������ �� ȸ���� Ȯ�� ����) +@ ȸ����x ��ȸ���� ������ ���� �� �� �־�ߵ� O
	@RequestMapping(value = "/faqdetail")
	public String faq6(Model mo, HttpServletRequest request, FAQDTO dto) {
		hs = request.getSession();
		Boolean FAQadmin = (Boolean) hs.getAttribute("adminloginstate");
		Boolean FAQmember = (Boolean) hs.getAttribute("loginstate");
		int cnum = Integer.parseInt(request.getParameter("cnum"));
		String openclose = request.getParameter("openclose");
		
		// 1.�����ڰ� �ƴϸ� 2.��ȸ���� + �������
		if (!FAQadmin && 
				!FAQmember 
				&& openclose.equals("�����")) {
			
			return "redirect:/faqout";
		}
		else { 
			fs = sqlSession.getMapper(FAQService.class);
	
			ArrayList<FAQDTO> list = fs.faqdetail(cnum);
			mo.addAttribute("list", list);
			fs.faqcount(cnum);
	
			ArrayList<FAQDTO> replylist = fs.faqreplydetail(cnum);
			mo.addAttribute("replylist", replylist);
	
			return "faqDtailview";
		}
	}

	// 1:1 ���Ǳ� �亯 Updateview(�����ڸ� �亯 ���� ����)
	@RequestMapping(value = "/faq_reply_update1")
	public String faq7admin(HttpServletRequest request, Model mo) throws IllegalStateException, IOException {
		int cnum = Integer.parseInt(request.getParameter("cnum"));

		fs = sqlSession.getMapper(FAQService.class);

		FAQDTO dto = fs.faq_reply_update1(cnum);
		mo.addAttribute("dto", dto);

		return "faq_reply_updateview";
	}

	// 1:1 ���Ǳ� �亯 Update clear(�̹��� max 3����� ����) +@ �ǵ��ư��� ��� �ʿ�
	@RequestMapping(value = "/faq_reply_update2", method = RequestMethod.POST)
	public String faq8admin(HttpServletResponse response, MultipartHttpServletRequest mul) throws IllegalStateException, IOException {
		int cnum = Integer.parseInt(mul.getParameter("cnum"));
		String tab = mul.getParameter("tab");
	    String title = mul.getParameter("title");
	    String nickname = mul.getParameter("nickname");
	    String fcontents = mul.getParameter("fcontents");

	    // ���� ó�� (������ ���� ��� �� ���ڿ��� ó��)
	    MultipartFile fimg1 = mul.getFile("fimage1");
	    MultipartFile fimg2 = mul.getFile("fimage2");
	    MultipartFile fimg3 = mul.getFile("fimage3");

	    String fname1 = (fimg1 != null && !fimg1.isEmpty()) ? fimg1.getOriginalFilename() : "";
	    String fname2 = (fimg2 != null && !fimg2.isEmpty()) ? fimg2.getOriginalFilename() : "";
	    String fname3 = (fimg3 != null && !fimg3.isEmpty()) ? fimg3.getOriginalFilename() : "";

	    // ���� ���� (������ ���� ��쿡�� ����)
	    if (!fname1.isEmpty()) {
	        fimg1.transferTo(new File(path + "\\" + fname1));
	    }
	    if (!fname2.isEmpty()) {
	        fimg2.transferTo(new File(path + "\\" + fname2));
	    }
	    if (!fname3.isEmpty()) {
	        fimg3.transferTo(new File(path + "\\" + fname3));
	    }
	    
	    // ���� �Ϸ� �˸�
		response.setContentType("text/html;charset=utf-8");
		PrintWriter prw=response.getWriter();
		prw.print("<script> alert('������ �Ϸ�Ǿ����ϴ�.');</script>");
		prw.print("<script> location.href='faqout';</script>");
		prw.close();
		
		fs = sqlSession.getMapper(FAQService.class);
		fs.faq_reply_update2(cnum, tab, title, fcontents, nickname, fname1, fname2, fname3);

		return "redirect:/faqout";
	}

	// 1:1 ���Ǳ� �亯 Delete clear
	@RequestMapping(value = "/faq_reply_delete")
	public String faq9admin(HttpServletRequest request) {
		int cnum = Integer.parseInt(request.getParameter("cnum"));
		fs = sqlSession.getMapper(FAQService.class);
		fs.faq_reply_delete(cnum);

		return "redirect:/faqout";
	}

	// 1:1 ���Ǳ� Updateview(ȸ���� ��������) +@ ��� ȸ���� ���������� ���� ���� �䱸 O
	@RequestMapping(value = "/faqupdate")
	public String faq10member(HttpServletRequest request, Model mo) throws IllegalStateException, IOException {
		int cnum = Integer.parseInt(request.getParameter("cnum"));

		fs = sqlSession.getMapper(FAQService.class);

		FAQDTO dto = fs.faqupdate(cnum);
		mo.addAttribute("dto", dto);

		return "faqUpdateview";
	}

	// 1:1 ���Ǳ� Update clear(�̹��� max 3����� ����) +@ �ǵ��ư��� ��� �ʿ�
	@RequestMapping(value = "/faqupdate2", method = RequestMethod.POST)
	public String faq11member(HttpServletResponse response, MultipartHttpServletRequest mul) throws IllegalStateException, IOException {
		int cnum = Integer.parseInt(mul.getParameter("cnum"));
		String tab = mul.getParameter("tab");
	    String title = mul.getParameter("title");
	    String nickname = mul.getParameter("nickname");
	    String fcontents = mul.getParameter("fcontents");
	    String openclose = mul.getParameter("openclose");

	    // ���� ó�� (������ ���� ��� �� ���ڿ��� ó��)
	    MultipartFile fimg1 = mul.getFile("fimage1");
	    MultipartFile fimg2 = mul.getFile("fimage2");
	    MultipartFile fimg3 = mul.getFile("fimage3");

	    String fname1 = (fimg1 != null && !fimg1.isEmpty()) ? fimg1.getOriginalFilename() : "";
	    String fname2 = (fimg2 != null && !fimg2.isEmpty()) ? fimg2.getOriginalFilename() : "";
	    String fname3 = (fimg3 != null && !fimg3.isEmpty()) ? fimg3.getOriginalFilename() : "";

	    // ���� ���� (������ ���� ��쿡�� ����)
	    if (!fname1.isEmpty()) {
	        fimg1.transferTo(new File(path + "\\" + fname1));
	    }
	    if (!fname2.isEmpty()) {
	        fimg2.transferTo(new File(path + "\\" + fname2));
	    }
	    if (!fname3.isEmpty()) {
	        fimg3.transferTo(new File(path + "\\" + fname3));
	    }
	    
	    // ���� �Ϸ� �˸�
		response.setContentType("text/html;charset=utf-8");
		PrintWriter prw=response.getWriter();
		prw.print("<script> alert('������ �Ϸ�Ǿ����ϴ�.');</script>");
		prw.print("<script> location.href='faqout';</script>");
		prw.close();

		fs = sqlSession.getMapper(FAQService.class);
		fs.faqupdate2(cnum, tab, title, fcontents, nickname, fname1, fname2, fname3, openclose);

		return "redirect:/faqout";
	}

	// 1:1 ���Ǳ� Delete clear
	@RequestMapping(value = "/faqdelete")
	public String faq12member(HttpServletRequest request) {
		int cnum = Integer.parseInt(request.getParameter("cnum"));
		fs = sqlSession.getMapper(FAQService.class);
		fs.faqdelete(cnum);

		return "redirect:/faqout";
	}

	// 1:1 ���Ǳ� �Խ��� �˻�(�˻� �� �Ⱓ,����,����,�ۼ���)
	@RequestMapping(value = "/faqsearch", method = RequestMethod.POST)
	public String faq13(HttpServletRequest request, Model mo) throws IllegalStateException, IOException {

		String faqkey1 = request.getParameter("faqkey1"); // �Ⱓ (1��, 1���� ��)
		String faqkey2 = request.getParameter("faqkey2"); // �˻� ���� (����, ����, �ۼ���)
		String faqvalue = request.getParameter("faqvalue"); // �˻���

		fs = sqlSession.getMapper(FAQService.class);
		ArrayList<FAQDTO> list = new ArrayList<>();

		// �� �� �Էµ� ���
		if (faqkey1 != null && !faqkey1.isEmpty() && faqkey2 != null && !faqkey2.isEmpty()) {
		    int days = Integer.parseInt(faqkey1); // faqkey1�� ���� 1, 7, 30, 90
		    if (faqkey2.equals("title")) {
		        list = fs.faqTitleSearchWithDate(faqvalue, days);
		    } else if (faqkey2.equals("fcontents")) {
		        list = fs.faqContentsSearchWithDate(faqvalue, days);
		    } else {
		        list = fs.faqNicknameSearchWithDate(faqvalue, days);
		    }
		}
		// faqkey1�� �Էµ� ��� (�Ⱓ ���͸�)
		else if (faqkey1 != null && !faqkey1.isEmpty()) {
		    int days = Integer.parseInt(faqkey1);
		    list = fs.faqSearchByDateOnly(days);
		}
		// faqkey2�� �Էµ� ��� (�˻� ���Ǹ�)
		else if (faqkey2 != null && !faqkey2.isEmpty()) {
		    if (faqkey2.equals("title")) {
		        list = fs.faqTitleSearch(faqvalue);
		    } else if (faqkey2.equals("fcontents")) {
		        list = fs.faqContentsSearch(faqvalue);
		    } else {
		        list = fs.faqNicknameSearch(faqvalue);
		    }
		}
	    
		mo.addAttribute("list", list);

		return "faqoutput";
	}
	
	// ������ Ȩ
	@RequestMapping(value = "/faq_community")
	public String faq14(Model mo) {

		fs = sqlSession.getMapper(FAQService.class);
		ArrayList<FAQDTO> bestfaq = fs.best_faq10();
		mo.addAttribute("bestfaq", bestfaq);

		return "faq_main";
	}

	// ������ Ȩ �˻�(�˻� �� �Ⱓ,����,����,�ۼ���)
	@RequestMapping(value = "/faq_main_serch", method = RequestMethod.POST)
	public String faq15(HttpServletRequest request, Model mo) throws IllegalStateException, IOException {

		String faqkey1 = request.getParameter("faqkey1"); // �Ⱓ (1��, 1���� ��)
		String faqkey2 = request.getParameter("faqkey2"); // �˻� ���� (����, ����, �ۼ���)
		String faqvalue = request.getParameter("faqvalue"); // �˻���

		fs2 = sqlSession.getMapper(FAQadminService.class);
		ArrayList<FAQadminDTO> list = new ArrayList<>();

		// �� �� �Էµ� ���
		if (faqkey1 != null && !faqkey1.isEmpty() && faqkey2 != null && !faqkey2.isEmpty()) {
		    int days = Integer.parseInt(faqkey1); // faqkey1�� ���� 1, 7, 30, 90
		    if (faqkey2.equals("title")) {
		        list = fs2.faqTitleSearchWithDate(faqvalue, days);
		    } else if (faqkey2.equals("fcontents")) {
		        list = fs2.faqContentsSearchWithDate(faqvalue, days);
		    } else {
		        list = fs2.faqNicknameSearchWithDate(faqvalue, days);
		    }
		}
		// faqkey1�� �Էµ� ��� (�Ⱓ ���͸�)
		else if (faqkey1 != null && !faqkey1.isEmpty()) {
		    int days = Integer.parseInt(faqkey1);
		    list = fs2.faqSearchByDateOnly(days);
		}
		// faqkey2�� �Էµ� ��� (�˻� ���Ǹ�)
		else if (faqkey2 != null && !faqkey2.isEmpty()) {
		    if (faqkey2.equals("title")) {
		        list = fs2.faqTitleSearch(faqvalue);
		    } else if (faqkey2.equals("fcontents")) {
		        list = fs2.faqContentsSearch(faqvalue);
		    } else {
		        list = fs2.faqNicknameSearch(faqvalue);
		    }
		}
		mo.addAttribute("bestfaq", list);

		return "faq_main";
	}

	// FAQ-���� ���� ���� �ۼ�(�����ڸ� �ۼ� ����)
	@RequestMapping(value = "/FAQ_in")
	public String faq16admin(HttpServletResponse response, HttpServletRequest request, Model mo) throws IOException {
		hs = request.getSession();
		Boolean FAQinput = (Boolean) hs.getAttribute("adminloginstate");

		// ������ �α����� �Ǿ� ���� ������ ���� �������� �����̷�Ʈ
		if (FAQinput == null || !FAQinput) {
			
			return "redirect:/faq_community";
		}
		return "faq_admin_input";
	}

	// FAQ-���� ���� ���� ����(�̹��� max 3����� ����) +@ ���������� �ٷ� �̵� ��� �䱸 O
	@RequestMapping(value = "/faq_admin_save", method = RequestMethod.POST)
	public String faq17admin(MultipartHttpServletRequest mul) throws IllegalStateException, IOException {

		String tab = mul.getParameter("tab");
	    String title = mul.getParameter("title");
	    String nickname = mul.getParameter("nickname");
	    String fcontents = mul.getParameter("fcontents");

	    // ���� ó�� (������ ���� ��� ""�� ���ؼ� �� ���ڿ��� ó��)
	    MultipartFile fimg1 = mul.getFile("fimage1");
	    MultipartFile fimg2 = mul.getFile("fimage2");
	    MultipartFile fimg3 = mul.getFile("fimage3");

	    String fname1 = (fimg1 != null && !fimg1.isEmpty()) ? fimg1.getOriginalFilename() : "";
	    String fname2 = (fimg2 != null && !fimg2.isEmpty()) ? fimg2.getOriginalFilename() : "";
	    String fname3 = (fimg3 != null && !fimg3.isEmpty()) ? fimg3.getOriginalFilename() : "";

	    // ���� ���� (������ ���� ��쿡�� ����)
	    if (!fname1.isEmpty()) {
	        fimg1.transferTo(new File(path + "\\" + fname1));
	    }
	    if (!fname2.isEmpty()) {
	        fimg2.transferTo(new File(path + "\\" + fname2));
	    }
	    if (!fname3.isEmpty()) {
	        fimg3.transferTo(new File(path + "\\" + fname3));
	    }

	    // FAQ ������ ����
	    fs2 = sqlSession.getMapper(FAQadminService.class);
	    fs2.faqinsert(tab, title, fcontents, nickname, fname1, fname2, fname3);

	    // �ۼ� �� ��ٷ� ���������� �̵���
	    int cnum = fs.save_detail();
	    		
	    //return "redirect:/faq_community";
	    return "redirect:/faq_questions_detail?cnum=" + cnum;
	}

	// FAQ-���� ���� ���� �Խ���(����¡)
	@RequestMapping(value = "/faq")
	public String faq18(HttpServletRequest request, PageDTO dto, Model mo) {
		String tab = request.getParameter("tab");
		String nowPage = request.getParameter("nowPage");
		String cntPerPage = request.getParameter("cntPerPage");

		fs2 = sqlSession.getMapper(FAQadminService.class);
		ArrayList<FAQadminDTO> list = fs2.faqboard(tab);
		mo.addAttribute("faq_admin_board", list);

		int total = fs2.total(tab);
		if (nowPage == null && cntPerPage == null) {
			nowPage = "1";
			cntPerPage = "10";
		} else if (nowPage == null) {
			nowPage = "1";
		} else if (cntPerPage == null) {
			cntPerPage = "10";
		}
		dto = new PageDTO(total, Integer.parseInt(nowPage), Integer.parseInt(cntPerPage));
		mo.addAttribute("paging", dto);
		mo.addAttribute("faq_admin_board", fs2.page(dto));

		return "faq_questions";
	}

	// FAQ-���� ���� ���� �˻�(�˻� �� �Ⱓ,����,����,�ۼ���)
	@RequestMapping(value = "/faq_questions_search", method = RequestMethod.POST)
	public String faq18_1(HttpServletRequest request, Model mo) throws IllegalStateException, IOException {

		String faqkey1 = request.getParameter("faqkey1"); // �Ⱓ (1��, 1���� ��)
		String faqkey2 = request.getParameter("faqkey2"); // �˻� ���� (����, ����, �ۼ���)
		String faqvalue = request.getParameter("faqvalue"); // �˻���

		fs2 = sqlSession.getMapper(FAQadminService.class);
		ArrayList<FAQadminDTO> list = new ArrayList<>();

		// �� �� �Էµ� ���
		if (faqkey1 != null && !faqkey1.isEmpty() && faqkey2 != null && !faqkey2.isEmpty()) {
		    int days = Integer.parseInt(faqkey1); // faqkey1�� ���� 1, 7, 30, 90
		    if (faqkey2.equals("title")) {
		        list = fs2.faqTitleSearchWithDate(faqvalue, days);
		    } else if (faqkey2.equals("fcontents")) {
		        list = fs2.faqContentsSearchWithDate(faqvalue, days);
		    } else {
		        list = fs2.faqNicknameSearchWithDate(faqvalue, days);
		    }
		}
		// faqkey1�� �Էµ� ��� (�Ⱓ ���͸�)
		else if (faqkey1 != null && !faqkey1.isEmpty()) {
		    int days = Integer.parseInt(faqkey1);
		    list = fs2.faqSearchByDateOnly(days);
		}
		// faqkey2�� �Էµ� ��� (�˻� ���Ǹ�)
		else if (faqkey2 != null && !faqkey2.isEmpty()) {
		    if (faqkey2.equals("title")) {
		        list = fs2.faqTitleSearch(faqvalue);
		    } else if (faqkey2.equals("fcontents")) {
		        list = fs2.faqContentsSearch(faqvalue);
		    } else {
		        list = fs2.faqNicknameSearch(faqvalue);
		    }
		}
		    
		mo.addAttribute("faq_admin_board", list);

		return "faq_questions";
		}
		
	// FAQ-���� ���� ���� ��������(�������� ��� + ȸ�� ��� ���� ���)
	@RequestMapping(value = "/faq_questions_detail")
	public String faq19(Model mo, HttpServletRequest request) {
		int cnum = Integer.parseInt(request.getParameter("cnum"));

		fs2 = sqlSession.getMapper(FAQadminService.class);

		FAQadminDTO faq = fs2.faq_questions_detail(cnum);
		mo.addAttribute("faq", faq);
		fs2.faqcount2(cnum);

		ArrayList<FAQadminDTO> faqreply = fs2.faq_questions_reply(cnum);
		mo.addAttribute("faqreply", faqreply);

		return "faq_questions_Detailview";
	}

	// FAQ-���� ���� ���� Updateview(�����ڸ� ��������)
	@RequestMapping(value = "/faq_admin_update")
	public String faq20admin(Model mo, HttpServletRequest request) {
		int cnum = Integer.parseInt(request.getParameter("cnum"));

		fs2 = sqlSession.getMapper(FAQadminService.class);
		FAQadminDTO dto = fs2.faq_admin_update(cnum);
		mo.addAttribute("dto", dto);

		return "faq_questions_Updateview";
	}

	// FAQ-���� ���� ���� Update clear(�̹��� max 3����� ����) +@ �ǵ��ư��� ��� �ʿ�...�̤�
	@RequestMapping(value = "/faq_admin_update2", method = RequestMethod.POST)
	public String faq21admin(HttpServletResponse response, MultipartHttpServletRequest mul) throws IllegalStateException, IOException {
		int cnum = Integer.parseInt(mul.getParameter("cnum"));
		String tab = mul.getParameter("tab");
	    String title = mul.getParameter("title");
	    String nickname = mul.getParameter("nickname");
	    String fcontents = mul.getParameter("fcontents");

	    // ���� ó�� (������ ���� ��� �� ���ڿ��� ó��)
	    MultipartFile fimg1 = mul.getFile("fimage1");
	    MultipartFile fimg2 = mul.getFile("fimage2");
	    MultipartFile fimg3 = mul.getFile("fimage3");

	    String fname1 = (fimg1 != null && !fimg1.isEmpty()) ? fimg1.getOriginalFilename() : "";
	    String fname2 = (fimg2 != null && !fimg2.isEmpty()) ? fimg2.getOriginalFilename() : "";
	    String fname3 = (fimg3 != null && !fimg3.isEmpty()) ? fimg3.getOriginalFilename() : "";

	    // ���� ���� (������ ���� ��쿡�� ����)
	    if (!fname1.isEmpty()) {
	        fimg1.transferTo(new File(path + "\\" + fname1));
	    }
	    if (!fname2.isEmpty()) {
	        fimg2.transferTo(new File(path + "\\" + fname2));
	    }
	    if (!fname3.isEmpty()) {
	        fimg3.transferTo(new File(path + "\\" + fname3));
	    }
	    
	    // ���� �Ϸ� �˸�
		response.setContentType("text/html;charset=utf-8");
		PrintWriter prw=response.getWriter();
		prw.print("<script> alert('������ �Ϸ�Ǿ����ϴ�.');</script>");
		prw.print("<script> location.href='faq';</script>");
		prw.close();
		
		fs2 = sqlSession.getMapper(FAQadminService.class);
		fs2.faq_admin_update2(cnum, tab, title, fcontents, nickname, fname1, fname2, fname3);
		int cnum1 = fs2.update_detail();
		
		return "redirect:/faq_questions_detail?cnum=" + cnum1;
	}

	// FAQ-���� ���� ���� Delete clear
	@RequestMapping(value = "/faq_admin_delete")
	public String faq22admin(Model mo, HttpServletRequest request) {
		int cnum = Integer.parseInt(request.getParameter("cnum"));

		fs2 = sqlSession.getMapper(FAQadminService.class);
		fs2.faq_admin_delete(cnum);

		return "redirect:/faq";
	}

	// FAQ-���� ���� ���� ���(ȸ���� ��� ����)
	@RequestMapping(value = "/faq_questions_reply_save", method = RequestMethod.POST)
	public String faq23member(HttpServletResponse response, HttpServletRequest request, Model mo) throws IOException {
		
		hs = request.getSession();
		Boolean FAQinput = (Boolean) hs.getAttribute("loginstate");
		
		if (FAQinput == null || !FAQinput) {
		
			response.setContentType("text/html;charset=utf-8");
			PrintWriter pww=response.getWriter();
			pww.print("<script> alert('�α��� �� �̿����ֻ���')</script>");
			pww.print("<script> location.href='login'</script>");
			pww.close();
			return "";			
			
		}
		else
		{
			int cnum = Integer.parseInt(request.getParameter("cnum"));
			int groups = Integer.parseInt(request.getParameter("groups"));
			int step = Integer.parseInt(request.getParameter("step"));
			int indent = Integer.parseInt(request.getParameter("indent"));
			String fcontents = request.getParameter("fcontents");
			String nickname = request.getParameter("nickname");
			String tab = request.getParameter("tab");
			fs2 = sqlSession.getMapper(FAQadminService.class);
			fs2.faq_questions_stepup(groups, step);
			step++;
			indent++;
	
			fs2.faq_questions_faqreplysave(cnum, groups, step, indent, fcontents, tab, nickname);
	
			return "redirect:/faq_questions_detail?cnum=" + cnum;

		}
	}

	
}

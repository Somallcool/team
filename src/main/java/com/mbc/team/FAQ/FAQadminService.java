package com.mbc.team.FAQ;

import java.util.ArrayList;

public interface FAQadminService {
//1. FAQ-���ֹ������� ����(���Ǳ� �ۼ�)
	void faqinsert(String tab, String title, String fcontents, String nickname, String fname1, String fname2,
			String fname3);
	
	int save_detail();

//2. FAQ-���ֹ������� �Խ���
	ArrayList<FAQadminDTO> faqboard(String tab);

//3. FAQ-���ֹ������� ������	
	public int total(String tab);

	public ArrayList<FAQadminDTO> page(PageDTO dto);

//4. FAQ-���ֹ������� ��������
	FAQadminDTO faq_questions_detail(int cnum);

//5. FAQ-���ֹ������� �������� ����(������)
	FAQadminDTO faq_admin_update(int cnum);
	
	int update_detail();

//5-1. ���� �Ϸ�(������)
	void faq_admin_update2(int cnum, String tab, String title, String fcontents, String nickname, String fname1,
			String fname2, String fname3);

//6. FAQ-���ֹ������� ����
	void faq_admin_delete(int cnum);

//7. FAQ-���ֹ������� ��� �ޱ�
	void faq_questions_stepup(int groups, int step);

	void faq_questions_faqreplysave(int cnum, int groups, int step, int indent, String fcontents, String tab, String nickname);

	ArrayList<FAQadminDTO> faq_questions_reply(int cnum);

//8. FAQ-���ֹ������� ��ȸ��
	void faqcount2(int cnum);

//9. FAQ-���ֹ������� �˻�
	
	ArrayList<FAQadminDTO> faqTitleSearchWithDate(String faqvalue, int days);

	ArrayList<FAQadminDTO> faqContentsSearchWithDate(String faqvalue, int days);

	ArrayList<FAQadminDTO> faqNicknameSearchWithDate(String faqvalue, int days);

	ArrayList<FAQadminDTO> faqSearchByDateOnly(int days);

	ArrayList<FAQadminDTO> faqTitleSearch(String faqvalue);

	ArrayList<FAQadminDTO> faqContentsSearch(String faqvalue);

	ArrayList<FAQadminDTO> faqNicknameSearch(String faqvalue);



	
	

}

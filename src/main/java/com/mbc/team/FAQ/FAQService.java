package com.mbc.team.FAQ;

import java.util.ArrayList;

public interface FAQService {
//1. ����(���Ǳ� �ۼ�)
	void faqinsert(String tab, String title, String fcontents, String nickname, String fname1, String fname2,
			String fname3);

//2. �Խ���
	ArrayList<FAQDTO> faqboard();

//3. ���Ǳ� ��
	ArrayList<FAQDTO> faqdetail(int cnum);

//4. ���Ǳ� ����â
	FAQDTO faqupdate(int cnum);

//5. ���Ǳ� �����Ϸ�
	void faqupdate2(int cnum, String tab, String title, String fcontents, String nickname, String fname1, String fname2,
			String fname3);

//6. ���Ǳ� ����
	void faqdelete(int cnum);

//7. ������	
	public int total(String tab);

	public ArrayList<FAQDTO> page(PageDTO dto);

//8. ���� �亯
	ArrayList<FAQDTO> faqreply(int cnum);

//9-1. ���Ǿ�
	void faqstepup(int groups, int step);

//9-2. ���� �亯 ���� ����	
	void faqreplysave(int cnum, String tab, String title, String fcontents, String nickname, int groups, int step,
			int indent);

//9-3. ���� �亯 ���
	ArrayList<FAQDTO> faqreplydetail(int cnum);

//10. Ű�� �˻�
	// title(����)
	ArrayList<FAQDTO> faqTitleSearch(String faqvalue);

	// fcontents(����)
	ArrayList<FAQDTO> faqContentsSearch(String faqvalue);
	
	// nickname(�ۼ���)
	ArrayList<FAQDTO> faqNicknameSearch(String faqvalue);
	
	// fdate(�Ⱓ)
	ArrayList<FAQDTO> faqSearchByDateOnly(int days);
	
	// �Ⱓ+����
	ArrayList<FAQDTO> faqTitleSearchWithDate(String faqvalue, int days);
	
	ArrayList<FAQDTO> faqContentsSearchWithDate(String faqvalue, int days);
	
	ArrayList<FAQDTO> faqNicknameSearchWithDate(String faqvalue, int days);

//11. ��ȸ��
	void faqcount(int cnum);

//12. ��ȸ�������� ����
	ArrayList<FAQDTO> category1();

//13. �ֽż� ����
	ArrayList<FAQDTO> category2();

//14. ������ ���� Ȩ
	ArrayList<FAQDTO> best_faq10();
	
//15. �������� ���� �亯 ����â
	FAQDTO faq_reply_update1(int cnum);

//16. �������� ���� �亯 �����Ϸ�
	void faq_reply_update2(int cnum, String tab, String title, String fcontents, String nickname, String fname1,
			String fname2, String fname3);

//17. �������� ���� �亯 ����
	void faq_reply_delete(int cnum);

//18. ������ Ȩ �˻�	
	ArrayList<FAQDTO> faq_main_titlesearch(String faq_search_value);

	ArrayList<FAQDTO> faq_main_nicknamesearch(String faq_search_value);
//






	



}

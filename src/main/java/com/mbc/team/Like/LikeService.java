package com.mbc.team.Like;

import java.util.ArrayList;

public interface LikeService {
//1. ���ϱ�
	void like_insert(int itemnum, String id);

//2. ���
	ArrayList<LikeDTO> like_product(String id);

//3. �� ��ǰ ����
	void like_items_delete(int likenum);

}

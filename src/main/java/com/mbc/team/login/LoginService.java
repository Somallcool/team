package com.mbc.team.login;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface LoginService {

	LoginDTO logincheck(String id);

	LoginDTO myinfo(String id);

	LoginDTO updateview(String id);

	void memberupdate2(LoginDTO loginDTO);

	LoginDTO deleteview(String id);

	boolean delete2(String id);

	LoginDTO findid(Map<String, String> params);

	LoginDTO findpw(String id, String name, String email);

	void updatepw(String id, String pw);

	

	

	

}

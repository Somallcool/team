package com.mbc.team.login;

public class LoginDTO {
	
	String id,nickname,pw,name,birth,phone,phone1,phone2,address,email,fdomain,bdomain,grade_code,auth;
	int total;
	public LoginDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LoginDTO(String id, String nickname, String pw, String name, String birth, String phone, String phone1,
			String phone2, String address, String email, String fdomain, String bdomain, String grade_code, String auth,
			int total) {
		super();
		this.id = id;
		this.nickname = nickname;
		this.pw = pw;
		this.name = name;
		this.birth = birth;
		this.phone = phone;
		this.phone1 = phone1;
		this.phone2 = phone2;
		this.address = address;
		this.email = email;
		this.fdomain = fdomain;
		this.bdomain = bdomain;
		this.grade_code = grade_code;
		this.auth = auth;
		this.total = total;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPhone1() {
		return phone1;
	}
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}
	public String getPhone2() {
		return phone2;
	}
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFdomain() {
		return fdomain;
	}
	public void setFdomain(String fdomain) {
		this.fdomain = fdomain;
	}
	public String getBdomain() {
		return bdomain;
	}
	public void setBdomain(String bdomain) {
		this.bdomain = bdomain;
	}
	public String getGrade_code() {
		return grade_code;
	}
	public void setGrade_code(String grade_code) {
		this.grade_code = grade_code;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
	

	
}

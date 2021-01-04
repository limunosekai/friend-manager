package lim.java.homework;

import java.io.Serializable;

public class Friends implements Serializable{

	private static final long serialVersionUID = 1L;
	private String name;
	private String birth;
	private String age;
	private String sex;
	private String address;
	private String tel;
	private Boolean[] hobby;
	private String id;
	
	public Friends(String id, String name, String birth, String age, String sex, String address, String tel, Boolean[] hobby) {
		this.name = name;
		this.birth = birth;
		this.age = age;
		this.sex = sex;
		this.address = address;
		this.tel = tel;
		this.hobby = hobby;
		this.id = id;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Boolean[] getHobby() {
		return hobby;
	}

	public void setHobby(Boolean[] hobby) {
		this.hobby = hobby;
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


	public String getAge() {
		return age;
	}


	public void setAge(String age) {
		this.age = age;
	}


	public String getSex() {
		return sex;
	}


	public void setSex(String sex) {
		this.sex = sex;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}

package com.kkc.sql;

public class UserData {

	private int studentID;
	private String name;
	private int gender; //0 여자, 1 남자
	private String webMail;
	private String password;
	private float kindness;
	private String token;
	
//	public UserDate(int studentID, String name, int gender, String webMail, String password, float kindness) {
//		this.studentID = studentID;
//		this.name = name;
//		this.gender = gender;
//		this.webMail = webMail;
//		this.password = password;
//		this.kindness = kindness;
//	}
	
	public int getStudentID() {
		return studentID;
	}
	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public String getWebMail() {
		return webMail;
	}
	public void setWebMail(String webMail) {
		this.webMail = webMail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public float getKindness() {
		return kindness;
	}
	public void setKindness(float kindness) {
		this.kindness = kindness;
	}
	
	public String getFCMToken() {
		return token;
	}
	
	public void setFCMToken(String token) {
		this.token = token;
	}
}

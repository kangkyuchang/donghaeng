package com.kkc.chat;

public class Chat {

	private int chatNumber;
	private int studenID;
	private int chatID;
	private String name;
	private String content;
	private String dateTime;
	
	public int getChatNumber() {
		return chatNumber;
	}

	public void setChatNumber(int chatNumber) {
		this.chatNumber = chatNumber;
	}

	public String getDateTime() {
		return dateTime;
	}

	public int getChatID() {
		return chatID;
	}

	public void setChatID(int chatID) {
		this.chatID = chatID;
	}
	
	public int getStudenID() {
		return studenID;
	}
	
	public void setStudenID(int studenID) {
		this.studenID = studenID;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	
	public String getDate() {
		String[] dateTimeSplit = dateTime.split(" ");
		return dateTimeSplit[0];
	}

	public void setDate(String date) {
		String[] dateTimeSplit = dateTime.split(" ");
		this.dateTime = date + " " + dateTimeSplit[1];
	}

	public String getTime() {
		String[] dateTimeSplit = dateTime.split(" ");
		String[] timeSplit = dateTimeSplit[1].split(":");
		return timeSplit[0] + ":" + timeSplit[1];
	}

	public void setTime(String time) {
		String[] dateTimeSplit = dateTime.split(" ");
		this.dateTime = dateTimeSplit[0] + time + ":00";
	}
}

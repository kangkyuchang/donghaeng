package com.kkc.matching;

public class MatchingRoom {

	private int roomNum;
	private String date;
	private String time;
	private String from;
	private String to;
	private String gender; //남자만, 여자만, 모두
	private int maximum;
	private int master;
	private int member1;
	private int member2;
	private int member3;
	
	public void setRoomNum(int roomNum) {
		this.roomNum = roomNum;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setMaximum(int maximum) {
		this.maximum = maximum;
	}

	public void setMaster(int master) {
		this.master = master;
	}

	public void setMember1(int member1) {
		this.member1 = member1;
	}

	public void setMember2(int member2) {
		this.member2 = member2;
	}

	public void setMember3(int member3) {
		this.member3 = member3;
	}
	
	public void setDateTime(String dateTime) {
		String[] dateTimeSplit = dateTime.split(" ");
		date = dateTimeSplit[0];
		String[] timeSplit = dateTimeSplit[1].split(":");
		time = timeSplit[0] + ":" + timeSplit[1];
	}

	public int getRoomNum() {
		return roomNum;
	}

	public String getDate() {
		return date;
	}

	public String getTime() {
		return time;
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}

	public String getGender() {
		return gender;
	}

	public int getMaximum() {
		return maximum;
	}

	public int getMaster() {
		return master;
	}

	public int getMember1() {
		return member1;
	}

	public int getMember2() {
		return member2;
	}

	public int getMember3() {
		return member3;
	}
}

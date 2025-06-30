package com.kkc.review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.kkc.matching.MatchingRoom;
import com.kkc.sql.DatabaseUtil;

public class ReviewDAO {

	public MatchingRoom mr;
	public HashMap<Integer, String> userData;
	
	public ReviewDAO() {
		mr = new MatchingRoom();
		userData = new HashMap<Integer, String>();
	}
	
	private List<Integer> getReviewMembers(int studentID) {
		List<Integer> reviewMembers = new ArrayList<Integer>();
		String SQL = "SELECT * FROM dongguk_room_info WHERE master = ? AND close = 1 "
				+ "OR member1 = ? AND close = 1 "
				+ "OR member2 = ? AND close = 1 "
				+ "OR member3 = ? AND close = 1 ORDER BY date_time";
		try {
			Connection conn = DatabaseUtil.getConnection();
			PreparedStatement pstm = conn.prepareStatement(SQL);
			for(int i = 1; i <= 4; i++)
				pstm.setInt(i, studentID);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()) {
				reviewMembers.clear();
				int myIndex = 0;
				for(int i = 7; i <= 10; i++) {
					int memberID = rs.getInt(i);
					if(memberID == 0)
						continue;
					if(memberID != studentID)
						reviewMembers.add(memberID);
					else
						myIndex = i - 6;
				}
				myIndex += 12;
				int checkReview = rs.getInt(myIndex);
				if(checkReview == 1)
					continue;
				if(!reviewMembers.isEmpty()) {
					mr.setRoomNum(rs.getInt(1));
					mr.setDateTime(rs.getString(2));
					mr.setFrom(rs.getString(3));
					mr.setTo(rs.getString(4));
					mr.setGender(rs.getString(5));
					mr.setMaximum(rs.getInt(6));
					mr.setMaster(rs.getInt(7));
					mr.setMember1(rs.getInt(8));
					mr.setMember2(rs.getInt(9));
					mr.setMember3(rs.getInt(10));
					return reviewMembers;
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return reviewMembers;
	}
	
	private void searchUserName(List<Integer> reviewMembers) {
		String SQL = "SELECT user_ids, user_names FROM dongguk_user_info";
		for(int i = 0; i < reviewMembers.size(); i++) {
			int memberID = reviewMembers.get(i);
			if(i == 0)
				SQL += " WHERE";
			else
				SQL += " OR";
			SQL += " user_ids = " + memberID;
		}
		SQL += ";";
		try {
			Connection conn = DatabaseUtil.getConnection();
			Statement stm = (Statement) conn.createStatement();
			ResultSet rs = stm.executeQuery(SQL);
			while(rs.next()) {
				userData.put(rs.getInt(1), rs.getString(2));
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean getReview(int studentID) {
		List<Integer> reviewMembers = getReviewMembers(studentID);
		if(reviewMembers.isEmpty())
			return false;
		searchUserName(reviewMembers);
		return true;
	}
	
	public void updateReview(int roomNumber, int studentID, HashMap<Integer, Float> userData) {
		int result = setReviewRoom(roomNumber, studentID);
		if(result != -1) {
			setUserKindness(userData);
		}
	}
	
	private int setReviewRoom(int roomNumber, int studentID) {
		int myIndex = myReviewIndex(roomNumber, studentID);
		String columnName = "review_";
		switch(myIndex) {
		case -1:
			return myIndex;
		case 1:
			columnName += "master";
			break;
		case 2:
			columnName += "member1";
			break;
		case 3:
			columnName += "master2";
			break;
		case 4:
			columnName += "master3";
			break;
		}
		String SQL = "UPDATE dongguk_room_info SET " + columnName +  " = 1 WHERE room_number = ?";
		try {
			Connection conn = DatabaseUtil.getConnection();
			PreparedStatement pstm = conn.prepareStatement(SQL);
			pstm.setInt(1, roomNumber);
			return pstm.executeUpdate();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	private HashMap<Integer, Float> getUserKindess(Set<Integer> members) {
		HashMap<Integer, Float> map = new HashMap<Integer, Float>();
		String SQL = "SELECT user_ids, user_kindness FROM dongguk_user_info";
		boolean bool = true;
		for(int i : members) {
			if(bool) {
				SQL += " WHERE ";
				bool = false;
			}
			else
				SQL += " OR ";
			SQL += "user_ids = " + i;
		}
		try {
			Connection conn = DatabaseUtil.getConnection();
			Statement stm = (Statement) conn.createStatement();
			ResultSet rs = stm.executeQuery(SQL);
			while(rs.next()) {
				map.put(rs.getInt(1), rs.getFloat(2));
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	private void setUserKindness(HashMap<Integer, Float> addkindnessData) {
		HashMap<Integer, Float> map = getUserKindess(addkindnessData.keySet());
		String SQL = "UPDATE dongguk_user_info SET user_kindness = ? WHERE user_ids = ?";
		try {
			Connection conn = DatabaseUtil.getConnection();
			PreparedStatement pstm = conn.prepareStatement(SQL);
			for(int key : addkindnessData.keySet()) {
				float kindness = addkindnessData.get(key) + map.get(key);
				pstm.setFloat(1, kindness);
				pstm.setInt(2, key);
				pstm.execute();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private int myReviewIndex(int roomNumber, int studentID) {
		String SQL = "SELECT master, member1, member2, member3 FROM dongguk_room_info WHERE room_number = ?";
		try {
			Connection conn = DatabaseUtil.getConnection();
			PreparedStatement pstm = conn.prepareStatement(SQL);
			pstm.setInt(1, roomNumber);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()) {
				for(int i = 1; i <= 4; i++) {
					int member = rs.getInt(i);
					if(member == studentID)
						return i;
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}

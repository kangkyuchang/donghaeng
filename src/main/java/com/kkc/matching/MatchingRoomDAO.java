package com.kkc.matching;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.kkc.fcm.FCMServerSide;
import com.kkc.sql.DatabaseUtil;
import com.kkc.sql.UserDAO;
import com.kkc.sql.UserData;

public class MatchingRoomDAO {

//	public int getMyRoomNumber(int studentID) {
//		String SQL = "SELECT * FROM dongguk_room_info WHERE master = ? AND close = 0";
//		try {
//			Connection conn = DatabaseUtil.getConnection();
//			Statement stm = (Statement) conn.createStatement();
//			ResultSet rs = stm.executeQuery(SQL);
//			while(rs.next()) {
//				for(int i = 7; i <= 10; i++) {
//					if(rs.getInt(i) == studentID) {
//						if(rs.getInt(11) == 0) //방을 생성했다면
//							return rs.getInt(1);
//						else
//							return 0; //매칭 중
//					}
//				}
//			}
//		}
//		catch(Exception e) {
//			e.printStackTrace();
//		}
//		return -1;
//	}
	public MatchingRoom getMyScheduleRoom(int studentID) {
		MatchingRoom mr = new MatchingRoom();
		String SQL = "SELECT * FROM dongguk_room_info WHERE master = ? AND close = 0 "
				+ "OR member1 = ? AND close = 0 "
				+ "OR member2 = ? AND close = 0 "
				+ "OR member3 = ? AND close = 0 ORDER BY date_time";
		try {
			Connection conn = DatabaseUtil.getConnection();
			PreparedStatement pstm = conn.prepareStatement(SQL);
			for(int i = 1; i <= 4; i++)
				pstm.setInt(i, studentID);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()) {
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
				return mr;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public List<MatchingRoom> getMyRoom(int studentID) {
		List<MatchingRoom> mrList = new ArrayList<MatchingRoom>();
		String SQL = "SELECT * FROM dongguk_room_info WHERE master = ? AND close != 1 ORDER BY date_time";
		try {
			Connection conn = DatabaseUtil.getConnection();
			PreparedStatement pstm = conn.prepareStatement(SQL);
			pstm.setInt(1, studentID);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()) {
				MatchingRoom mr = new MatchingRoom();
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
				if(rs.getInt(11) == -1) {
					mrList.clear();
					mrList.add(mr);
					return mrList;
				}
				mrList.add(mr);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return mrList;
	}
	
	public List<MatchingRoom> getMyJoinedRoom(int studentID) {
		List<MatchingRoom> mrList = new ArrayList<MatchingRoom>();
		String SQL = "SELECT * FROM dongguk_room_info WHERE master = ? OR member1 = ? OR member2 = ? OR member3 = ?";
		try {
			Connection conn = DatabaseUtil.getConnection();
			PreparedStatement pstm = conn.prepareStatement(SQL);
			for(int i = 1; i <= 4; i++)
				pstm.setInt(i, studentID);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()) {
				MatchingRoom mr = new MatchingRoom();
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
				mrList.add(mr);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return mrList;
	}
	
	public MatchingRoom getRoomData(int roomNumber) {
		String SQL = "SELECT * FROM dongguk_room_info WHERE room_number = ?";
		try {
			Connection conn = DatabaseUtil.getConnection();
			PreparedStatement pstm = conn.prepareStatement(SQL);
			pstm.setInt(1, roomNumber);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()) {
				MatchingRoom mr = new MatchingRoom();
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
				return mr;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<MatchingRoom> getRoomData(int year, int month, int date) {
		String SQL = "SELECT * FROM dongguk_room_info WHERE date_time > ? AND date_time < ? AND close = 0";
		List<MatchingRoom> mrList = new ArrayList<MatchingRoom>();
		try {
			Connection conn = DatabaseUtil.getConnection();
			PreparedStatement pstm = conn.prepareStatement(SQL);
			Date tmpDate = new Date();
			tmpDate.setYear(year - 1900);
			tmpDate.setMonth(month);
			tmpDate.setDate(date + 1);
			pstm.setString(1, year + "-" + month + "-" + date);
			pstm.setString(2, (tmpDate.getYear()+1900) + "-" + tmpDate.getMonth() + "-" + tmpDate.getDate());
			ResultSet rs = pstm.executeQuery();
			while(rs.next()) {
				MatchingRoom mr = new MatchingRoom();
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
				mrList.add(mr);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return mrList;
	}
	
	public int signRoom(int studentID, int roomNumber, int gender, int weight) { // -3무게 초과, -2 짐 없이 탑승 가능, -1 가입 실패, 0 인원 초과, 1 가입 성공, 2 이미 존재
		String SQL = "SELECT maximum, gender, master, member1, member2, member3, close, load_weight FROM dongguk_room_info WHERE room_number = ?";
		try {
			Connection conn = DatabaseUtil.getConnection();
			PreparedStatement pstm = conn.prepareStatement(SQL);
			pstm.setInt(1, roomNumber);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()) {
				if(rs.getInt(7) != 0)
					continue;
				int index = 0;
				int num = 0;
				int totalWeight = rs.getInt(8) + weight;
				if(totalWeight > 5)
					return totalWeight - 4;
				for(int i = 3; i <= 6; i++) {
					String member = rs.getString(i);
					if(member != null) {
						if(!member.equals("0")) {
							if(rs.getInt(i) == studentID)
								return 2;
							num ++;
						}
						else {
							if(index == 0)
								index = i - 3;
						}
					}
					else {
						if(index == 0)
							index = i - 3;
					}
				}
				if(num >= rs.getInt(1)) 
					return 0;
				int companionGender = -1;
				switch(rs.getString(2)) {
				case "모두":
					companionGender = gender;
					break;
				case "남자":
					companionGender = 1;
					break;
				case "여자":
					companionGender = 0;
				}
				if(companionGender != gender)
					return -1;
				SQL = "UPDATE dongguk_room_info SET member? = ?, load_weight = ? WHERE room_number = ?";
				pstm = conn.prepareStatement(SQL);
				pstm.setInt(1, index);
				pstm.setInt(2, studentID);
				pstm.setInt(3, totalWeight);
				pstm.setInt(4, roomNumber);
				return pstm.executeUpdate();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public int match(MatchingRoom mr, UserData ud, int weight) { // -2 오류, -1 매칭 실패, 0 이미 매칭 중, 1 매칭 성공, 2 이미 가입되어 있음
		String SQL = "SELECT * FROM dongguk_room_info WHERE close = 0 AND date_time = ? OR close = -1 AND date_time = ?";
		try {
			Connection conn = DatabaseUtil.getConnection();
			PreparedStatement pstm = conn.prepareStatement(SQL);
			String dateTime = mr.getDate() + " " + mr.getTime();
			for(int i = 1; i <= 2; i++)
				pstm.setString(i, dateTime);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()) {
				if(rs.getInt(7) == ud.getStudentID() && rs.getInt(11) == -1)
					return 0;
				if(rs.getString(3).equals(mr.getFrom()) && rs.getString(4).equals(mr.getTo())) {
					final int close = rs.getInt(11);
					int index = 0;
					int num = 0;
					int totalWeight = rs.getInt(12) + weight;
					if(totalWeight > 5) 
						continue;
		
					for(int i = 7; i <= 10; i++) {
						if(rs.getString(i) != null) {
							if(!rs.getString(i).equals("0")) {
								if(rs.getInt(i) == ud.getStudentID())
									return 2; //return 으로 변경해야됨
								num ++;
							}
							else {
								if(index == 0)
									index = i - 7;
							}
						}
						else {
							if(index == 0)
								index = i - 7;
						}
					}
					if(num >= rs.getInt(6)) 
						continue;
					
					int companionGender = -1;
					switch(rs.getString(5)) {
					case "모두":
						companionGender = ud.getGender();
						break;
					case "남자":
						companionGender = 1;
						break;
					case "여자":
						companionGender = 0;
					}
					if(companionGender != ud.getGender()) 
						continue;
					
					SQL = "UPDATE dongguk_room_info SET member? = ?, load_weight = ?, close = 0 WHERE room_number = ?";
					pstm = conn.prepareStatement(SQL);
					pstm.setInt(1, index);
					pstm.setInt(2, ud.getStudentID());
					pstm.setInt(3, totalWeight);
					pstm.setInt(4, rs.getInt(1));
					int result = pstm.executeUpdate();
					if(result == 1 && close == -1) {
						int master = rs.getInt(7);
						UserDAO userDao = new UserDAO();
						String token = userDao.getUserToken(master);
						if(token != null)
							FCMServerSide.getInstance.sendMessage("알림", "매칭이 완료되었습니다.", token);
					}
					return result;
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			return -2;
		}
		createMatchingData(mr, weight);
		return -1;
	}
	
	public boolean isMatching(int studentID) {
		String SQL = "SELECT * FROM dongguk_room_info WHERE master = ? AND close = -1";
		try {
			Connection conn = DatabaseUtil.getConnection();
			PreparedStatement pstm = conn.prepareStatement(SQL);
			pstm.setInt(1, studentID);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()) {
				return true;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private void createMatchingData(MatchingRoom mr, int weight) {
		String SQL = "INSERT INTO dongguk_room_info VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			Connection conn = DatabaseUtil.getConnection();
			PreparedStatement pstm = conn.prepareStatement(SQL);
			String dateTime = mr.getDate() + " " + mr.getTime();
			pstm.setString(1, dateTime);
			pstm.setString(2, mr.getFrom());
			pstm.setString(3, mr.getTo());
			pstm.setString(4, mr.getGender());
			pstm.setInt(5, mr.getMaximum());
			pstm.setInt(6, mr.getMaster());
			pstm.setInt(7, 0);
			pstm.setInt(8, 0);
			pstm.setInt(9, 0);
			pstm.setInt(10, -1);
			pstm.setInt(11, weight);
			pstm.setInt(12, 0);
			pstm.setInt(13, 0);
			pstm.setInt(14, 0);
			pstm.setInt(15, 0);
			pstm.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private int getMatchingRoomAmount() {
		String SQL = "SELECT COUNT(*) FROM dongguk_room_info;";
		try {
			Connection conn = DatabaseUtil.getConnection();
			Statement stm = (Statement) conn.createStatement();
			ResultSet rs = stm.executeQuery(SQL);
			while(rs.next()) {
				return rs.getInt(1);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public void createMatchingRoom(int studentID) {
		String SQL = "SELECT room_number FROM dongguk_room_info WHERE master = ? AND close = -1";
		try {
			Connection conn = DatabaseUtil.getConnection();
			PreparedStatement pstm = conn.prepareStatement(SQL);
			pstm.setInt(1, studentID);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()) {
				SQL = "UPDATE dongguk_room_info SET close = 0 WHERE room_number = ?";
				pstm = conn.prepareStatement(SQL);
				pstm.setInt(1, rs.getInt(1));
				pstm.executeUpdate();
				return;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}

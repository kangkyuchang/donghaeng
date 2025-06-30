package com.kkc.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

	public int join(int studentID, String userName, String userPassword, int gender, String userWebMail) { //데이터베이스 서버에서 컬럼 추가하는 메소드
		String SQL = "INSERT INTO dongguk_user_info VALUES (?, ?, ?, ?, ?, ?, ?)";
		try {
			Connection conn = DatabaseUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, studentID);
			pstmt.setString(2, userName);
			pstmt.setInt(3, gender);
			pstmt.setString(4, userWebMail);
			pstmt.setString(5, userPassword);
			pstmt.setFloat(6, 36.5F);
			pstmt.setString(7, null);
			int result = pstmt.executeUpdate(); //1
			return result;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public UserData login(int id, String password) {
		String SQL = "SELECT * FROM dongguk_user_info WHERE user_ids = ?";
		try {
			Connection conn = DatabaseUtil.getConnection();
			PreparedStatement stm = conn.prepareStatement(SQL);
			stm.setInt(1, id);
			ResultSet rs = stm.executeQuery();
			while(rs.next()) {
				if(password.equals(rs.getString(5))) {
					UserData ud = new UserData();
					ud.setStudentID(rs.getInt(1));
					ud.setName(rs.getString(2));
					ud.setGender(rs.getInt(3));
					ud.setWebMail(rs.getString(4));
					ud.setPassword(rs.getString(5));
					ud.setKindness(rs.getFloat(6));
					ud.setFCMToken(rs.getString(7));
					return ud;
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getUserToken(int studentID) {
		String SQL = "SELECT user_token FROM dongguk_user_info WHERE user_ids = ?";
		try {
			Connection conn = DatabaseUtil.getConnection();
			PreparedStatement stm = conn.prepareStatement(SQL);
			stm.setInt(1, studentID);
			ResultSet rs = stm.executeQuery();
			while(rs.next()) {
				return rs.getString(1);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int checkWebMailDuplication(String webMail) {
		String SQL = "SELECT * FROM dongguk_user_info WHERE user_web_mail = ?";
		try {
			Connection conn = DatabaseUtil.getConnection();
			PreparedStatement stm = conn.prepareStatement(SQL);
			stm.setString(1, webMail);
			ResultSet result = stm.executeQuery();
			int rs = result.next() ? 1 : 0;
			return rs;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}

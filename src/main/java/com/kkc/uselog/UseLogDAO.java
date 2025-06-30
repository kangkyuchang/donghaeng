package com.kkc.uselog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.kkc.matching.MatchingRoom;
import com.kkc.sql.DatabaseUtil;

public class UseLogDAO {

	public List<MatchingRoom> getUseLogs(int studentID, String dateTime) {
		List<MatchingRoom> roomList = new ArrayList<MatchingRoom>();
		String SQL = "SELECT * FROM dongguk_room_info WHERE master = ? AND date_time < ? OR member1 = ? AND date_time < ? OR member2 = ? AND date_time < ? OR member3 = ? AND date_time < ? ORDER BY date_time DESC";
		try {
			Connection conn = DatabaseUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			for(int i = 1; i <=7; i+=2) {
				pstmt.setInt(i, studentID);
				pstmt.setString(i+1, dateTime);
			}
			ResultSet rs = pstmt.executeQuery();
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
				roomList.add(mr);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return roomList;
	}
}

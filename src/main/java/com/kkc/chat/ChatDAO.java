package com.kkc.chat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.kkc.matching.MatchingRoom;
import com.kkc.matching.MatchingRoomDAO;
import com.kkc.sql.DatabaseUtil;

public class ChatDAO {
	
	public List<MatchingRoom> getMyChatRooms(int studentID) {
		MatchingRoomDAO dao = new MatchingRoomDAO();
		List<MatchingRoom> mrList = dao.getMyJoinedRoom(studentID);
		int index = 0;
		String SQL = "SELECT chat_id FROM dongguk_chat_info WHERE student_id = ? ORDER BY chat_time DESC";
		try {
			Connection conn = DatabaseUtil.getConnection();
			PreparedStatement pstm = conn.prepareStatement(SQL);
			pstm.setInt(1, studentID);
			ResultSet rs = pstm.executeQuery();
			Set<Integer> duplication = new HashSet<Integer>();
			while(rs.next()) {
				int chatID = rs.getInt(1);
				if(duplication.contains(chatID))
					continue;
				for(int i = 0; i < mrList.size(); i++) {
					MatchingRoom mr = mrList.get(i);
					if(mr.getRoomNum() == chatID) {
						mrList.remove(i);
						mrList.add(index, mr);
						index ++;
						duplication.add(chatID);
					}
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return mrList;
	}

	public List<Chat> loadChatDatas(int chatID, String dateTime, int loadedChatNum) {
		List<Chat> chatList = new ArrayList<Chat>();
		String SQL = "SELECT * FROM dongguk_chat_info WHERE chat_id = ? AND chat_time <= ? ORDER BY chat_number DESC";
		try {
			Connection conn = DatabaseUtil.getConnection();
			PreparedStatement pstm = conn.prepareStatement(SQL);
			pstm.setInt(1, chatID);
			pstm.setString(2, dateTime);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()) {
				if(rs.getInt(1) >= loadedChatNum && loadedChatNum != -1)
					continue;
				if(chatList.size() >= 10)
					return chatList;
				Chat chat = new Chat();
				chat.setChatNumber(rs.getInt(1));
				chat.setChatID(rs.getInt(2));
				chat.setStudenID(rs.getInt(3));
				chat.setName(rs.getString(4));
				chat.setContent(rs.getString(5));
				chat.setDateTime(rs.getString(6));
				chatList.add(chat);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return chatList;
	}
	
	public Chat getRecentChat(int chatID, String dateTime) {
		String SQL = "SELECT * FROM dongguk_chat_info WHERE chat_id = ? AND chat_time <= ? ORDER BY chat_number DESC";
		try {
			Connection conn = DatabaseUtil.getConnection();
			PreparedStatement pstm = conn.prepareStatement(SQL);
			pstm.setInt(1, chatID);
			pstm.setString(2, dateTime);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()) {
				Chat chat = new Chat();
				chat.setChatNumber(rs.getInt(1));
				chat.setChatID(rs.getInt(2));
				chat.setStudenID(rs.getInt(3));
				chat.setName(rs.getString(4));
				chat.setContent(rs.getString(5));
				chat.setDateTime(rs.getString(6));
				return chat;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int chat(Chat chat) {
		String SQL = "INSERT INTO dongguk_chat_info VALUES (null, ?, ?, ?, ?, ?)";
		try {
			Connection conn = DatabaseUtil.getConnection();
			PreparedStatement pstm = conn.prepareStatement(SQL);
			pstm.setInt(1, chat.getChatID());
			pstm.setInt(2, chat.getStudenID());
			pstm.setString(3, chat.getName());
			pstm.setString(4, chat.getContent());
			String dateTime = chat.getDate() + " " + chat.getTime();
			pstm.setString(5, dateTime);
			return pstm.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public List<Integer> getChattingMembers(int roomNumber) {
		List<Integer> list = new ArrayList<Integer>();
		String SQL = "SELECT master, member1, member2, member3 FROM dongguk_room_info WHERE room_number = ?";
		try {
			Connection conn = DatabaseUtil.getConnection();
			PreparedStatement pstm = conn.prepareStatement(SQL);
			pstm.setInt(1, roomNumber);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()) {
				for(int i = 1; i <= 4; i++) {
					int studentID = rs.getInt(i);
					if(studentID != 0)
						list.add(studentID);
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}

package com.kkc.fcm;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.kkc.sql.DatabaseUtil;

public class FirebaseClouldMessagingDAO {

	public int tokenUpdate(int sutdentID, String token) {
		String SQL = "UPDATE dongguk_user_info SET user_token = ? WHERE user_ids = ?";
		try {
			Connection conn = DatabaseUtil.getConnection();
			PreparedStatement stm = conn.prepareStatement(SQL);
			stm.setString(1, token);
			stm.setInt(2, sutdentID);
			return stm.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}

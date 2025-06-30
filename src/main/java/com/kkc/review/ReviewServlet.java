package com.kkc.review;

import java.io.IOException;
import java.util.HashMap;

import com.kkc.login.Login;
import com.kkc.matching.MatchingRoom;
import com.kkc.sql.UserData;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("studentID") == null || request.getParameter("password") == null) {
			response.getWriter().write("-1");
			return;
		}
		
		int studentID = Integer.parseInt(request.getParameter("studentID"));
		String password = request.getParameter("password");
		
		if(Login.loginData.containsKey(studentID)) {
			UserData ud = Login.loginData.get(studentID);
			if(!ud.getPassword().equals(password)) {
				response.getWriter().write("-1");
				return;
			}
			ReviewDAO dao = new ReviewDAO();
			if(dao.getReview(studentID)) {
				response.getWriter().write(getJSON(dao.mr, dao.userData));
				return;
			}
		}
		response.getWriter().write("{ \"room_information\" : \"없음\" }");
	}
	
	private String getJSON(MatchingRoom mr, HashMap<Integer, String> userData) {
		StringBuffer sb = new StringBuffer("");
		sb.append("{\"room_information\": ");
		sb.append("{");
		sb.append("\"number\": \"" + mr.getRoomNum() + "\",");
		sb.append("\"date\": \"" + mr.getDate() + "\",");
		sb.append("\"time\": \"" + mr.getTime() + "\",");
		sb.append("\"from\": \"" + mr.getFrom() + "\",");
		sb.append("\"to\": \"" + mr.getTo() + "\",");
		sb.append("\"gender\": \"" + mr.getGender() + "\",");
		sb.append("\"maximum\": \"" + mr.getMaximum() + "\",");
		sb.append("\"master\": \"" + mr.getMaster() + "\",");
		sb.append("\"members\": [\"" + mr.getMember1() + "\",");
		sb.append("\"" + mr.getMember2() + "\",");
		sb.append("\"" + mr.getMember3() + "\"]}, ");
		sb.append("\"user_information\": ");
		sb.append("[");
		int count = 1;
		for(int key : userData.keySet()) {
			if(count != 1)
				sb.append(", ");
			sb.append("{\"studentID\": \"" + key + "\",");
			sb.append("\"name\": \"" + userData.get(key) + "\"}");
			count ++;
		}
		
		sb.append("]}");
		return sb.toString();
	}

}

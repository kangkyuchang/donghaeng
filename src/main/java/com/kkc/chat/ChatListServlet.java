package com.kkc.chat;

import java.io.IOException;
import java.util.List;

import com.kkc.login.Login;
import com.kkc.matching.MatchingRoom;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ChatListServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("studentID") == null || request.getParameter("password") == null)
			return;
		int studentID = Integer.parseInt(request.getParameter("studentID"));
		String password = request.getParameter("password");
		if(!Login.checkLogin(studentID, password))
			return;
		
		ChatDAO dao = new ChatDAO();
		response.getWriter().write(getJSON(dao.getMyChatRooms(studentID)));
	}
	
	private String getJSON(List<MatchingRoom> mrList) {
		StringBuffer sb = new StringBuffer("");
		sb.append("{\"room_information\": [");
		boolean first = true;
		for(MatchingRoom mr : mrList) {
			if(!first) 
				sb.append(", ");
			else
				first = false;
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
			sb.append("\"" + mr.getMember3() + "\"]}");
		}
		sb.append("]}");
		return sb.toString();
	}
}

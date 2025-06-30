package com.kkc.matching;

import java.io.IOException;
import java.util.List;

import com.kkc.login.Login;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MyRoom extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MatchingRoomDAO dao = new MatchingRoomDAO();
		
		if(request.getParameter("studentID") == null || request.getParameter("password") == null) {
			response.getWriter().write("{ \"room_information\": [ \"오류\" ]}");
			return;
		}
		
		int studentID = Integer.parseInt(request.getParameter("studentID"));
		String password = request.getParameter("password");
		if(!Login.checkLogin(studentID, password)) {
			response.getWriter().write("{ \"room_information\": [ \"오류\" ]}");
			return;
		}
		List<MatchingRoom> mrList = dao.getMyRoom(studentID);
		if(mrList.size() > 0) 
			response.getWriter().write(getJSON(mrList, dao.isMatching(studentID)));
		else
			response.getWriter().write("{ \"room_information\": [ \"없음\" ]}");
	}
	
	private String getJSON(List<MatchingRoom> matchingRooms, boolean bool) {
		StringBuffer sb = new StringBuffer("");
		sb.append("{\"room_information\": [");
		boolean first = true;
		for(MatchingRoom mr : matchingRooms) {
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
		sb.append("], ");
		sb.append("\"is_matching\" : \"" + bool + "\"}");
		return sb.toString();
	}
}

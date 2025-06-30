package com.kkc.matching;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.kkc.login.Login;

public class MySchedule extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MatchingRoomDAO dao = new MatchingRoomDAO();
		
		if(request.getParameter("studentID") == null || request.getParameter("password") == null) {
			response.getWriter().write("{ \"room_information\": \"오류\" }");
			return;
		}
		
		int studentID = Integer.parseInt(request.getParameter("studentID"));
		String password = request.getParameter("password");
		if(!Login.checkLogin(studentID, password)) {
			response.getWriter().write("{ \"room_information\": \"오류\" }");
			return;
		}
		MatchingRoom mr = dao.getMyScheduleRoom(studentID);
		if(mr != null) 
			response.getWriter().write(getJSON(mr));
		else
			response.getWriter().write("{ \"room_information\": \"없음\" }");
	}
	
	private String getJSON(MatchingRoom mr) {
		StringBuffer sb = new StringBuffer("");
		sb.append("{\"room_information\": {");
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
		sb.append("\"" + mr.getMember3() + "\"]}}");
		return sb.toString();
	}
}

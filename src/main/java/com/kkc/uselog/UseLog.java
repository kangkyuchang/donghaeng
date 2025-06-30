package com.kkc.uselog;

import java.io.IOException;
import java.util.List;

import com.kkc.matching.MatchingRoom;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UseLog extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int year = 0, month = 0, date = 0, hour = 0, minute = 0;
		if(request.getParameter("year") != null) {
			year = Integer.parseInt(request.getParameter("year"));
		}
		if(request.getParameter("month") != null) {
			month = Integer.parseInt(request.getParameter("month"));
		}
		if(request.getParameter("date") != null) {
			date = Integer.parseInt(request.getParameter("date"));
		}
		if(request.getParameter("hour") != null) {
			hour = Integer.parseInt(request.getParameter("hour"));
		}
		if(request.getParameter("minute") != null) {
			minute = Integer.parseInt(request.getParameter("minute"));
		}
		int studentID = request.getParameter("studentID") != null ? Integer.parseInt(request.getParameter("studentID")) : 0;
		UseLogDAO useLogDAO = new UseLogDAO();
		String dateTime = year + "-" + month + "-" + date + " " + hour + ":" + minute;
		List<MatchingRoom> roomList = useLogDAO.getUseLogs(studentID, dateTime);
		response.getWriter().write(getJSON(roomList));
	}

	private String getJSON(List<MatchingRoom> roomList) {
		StringBuffer sb = new StringBuffer("");
		sb.append("{\"room_information\": [");
		boolean first = true;
		for(MatchingRoom mr : roomList) {
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

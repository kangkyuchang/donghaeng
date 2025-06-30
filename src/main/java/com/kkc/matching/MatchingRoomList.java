package com.kkc.matching;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/MatchingRoomList")
public class MatchingRoomList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		System.out.println("get");
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int year = Integer.parseInt(request.getParameter("year"));
		int month = Integer.parseInt(request.getParameter("month"));
		int date = Integer.parseInt(request.getParameter("date"));
		response.getWriter().write(getJSON(year, month, date));
	}
	
	public String getJSON(int year, int month, int date) {
		StringBuffer sb = new StringBuffer("");
		sb.append("{\"room_information\": [");
		boolean first = true;
		MatchingRoomDAO dao = new MatchingRoomDAO();
		for(MatchingRoom mr : dao.getRoomData(year, month, date)) {
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

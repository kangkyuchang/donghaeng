package com.kkc.matching;

import java.io.IOException;

import com.kkc.login.Login;
import com.kkc.sql.UserData;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MatchingServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("studentID") == null || request.getParameter("dateTime") == null
				|| request.getParameter("from") == null || request.getParameter("to") == null
				|| request.getParameter("gender") == null || request.getParameter("maximum") == null
				|| request.getParameter("weight") == null || request.getParameter("password") == null) {
			response.getWriter().write("-2");
			return;
		}
		
		int studentID = Integer.parseInt(request.getParameter("studentID"));
		String password = request.getParameter("password");
		String dateTime = request.getParameter("dateTime");
		String from = request.getParameter("from");
		String to = request.getParameter("to");
		String gender = request.getParameter("gender");
		int maximum = Integer.parseInt(request.getParameter("maximum"));
		int weight = Integer.parseInt(request.getParameter("weight"));
		if(Login.loginData.containsKey(studentID)) {
			UserData ud = Login.loginData.get(studentID);
			if(ud.getPassword().equals(password)) {
				MatchingRoomDAO dao = new MatchingRoomDAO();
				if(dao.isMatching(studentID)) {
					response.getWriter().write("0");
					return;
				}
				if(gender.equals("동성")) 
					gender = ud.getGender() == 0 ? "여자" : "남자";
				MatchingRoom mr = new MatchingRoom();
				mr.setDateTime(dateTime);
				mr.setFrom(from);
				mr.setTo(to);
				mr.setGender(gender);
				mr.setMaximum(maximum);
				mr.setMaster(studentID);
				
				
				response.getWriter().write("" + dao.match(mr, ud, weight));
				return;
			}
		}
		response.getWriter().write("-2");
	}

}

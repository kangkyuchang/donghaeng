package com.kkc.matching;

import java.io.IOException;

import com.kkc.login.Login;
import com.kkc.sql.UserData;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MatchingRoomSignServelt extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("studentID") == null || request.getParameter("password") == null 
				|| request.getParameter("roomNumber") == null || request.getParameter("weight") == null) {
			response.getWriter().write("-1");
			return;
		}
		
		int studentID = Integer.parseInt(request.getParameter("studentID"));
		String password = request.getParameter("password");
		int roomNumber = Integer.parseInt(request.getParameter("roomNumber"));
		int weight = Integer.parseInt(request.getParameter("weight"));
		
		if(roomNumber <= 0) {
			response.getWriter().write("-1");
			return;
		}
		
		if(Login.loginData.containsKey(studentID)) {
			UserData ud = Login.loginData.get(studentID);
			if(!ud.getPassword().equals(password)) {
				response.getWriter().write("-1");
				return;
			}
			MatchingRoomDAO dao = new MatchingRoomDAO();
			response.getWriter().write("" + dao.signRoom(studentID, roomNumber, ud.getGender(), weight));
		}
	}
}

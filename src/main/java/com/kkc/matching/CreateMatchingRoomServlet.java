package com.kkc.matching;

import java.io.IOException;

import com.kkc.login.Login;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/createMatchingRoom")
public class CreateMatchingRoomServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("studentID") == null || request.getParameter("password") == null)
			return;
		int studentID = Integer.parseInt(request.getParameter("studentID"));
		String password = request.getParameter("password");
		if(!Login.checkLogin(studentID, password))
			return;
		MatchingRoomDAO dao = new MatchingRoomDAO();
		dao.createMatchingRoom(studentID);
	}

}

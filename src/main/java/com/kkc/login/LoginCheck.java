package com.kkc.login;

import java.io.IOException;

import com.kkc.sql.UserData;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int studentID = Integer.parseInt(request.getParameter("studentID"));
		String password = request.getParameter("password");
		if(Login.loginData.containsKey(studentID)) {
			UserData ud = Login.loginData.get(studentID);
			if(ud.getPassword().equals(password)) {
				response.getWriter().write(Login.getLoginJSON("success", ud));
				return;
			}
		}
		response.getWriter().write(Login.getLoginJSON("failure", null));
	}
}

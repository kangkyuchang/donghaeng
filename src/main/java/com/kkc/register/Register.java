package com.kkc.register;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.kkc.sql.UserDAO;

@WebServlet("/registerAction")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter();
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		for(String key : request.getParameterMap().keySet()) {
//			System.out.println("key: " + key + ", value: " + request.getParameterMap().get(key)[0]);
//		}
		request.setCharacterEncoding("utf-8");
		UserDAO userDAO = new UserDAO();
		if(request.getParameter("userName") == null || request.getParameter("studentId") == null
				|| request.getParameter("password") == null || request.getParameter("gender") == null
				|| request.getParameter("webMail") == null || request.getParameter("inputCode") == null)
			return;
		String userName = (String) request.getParameter("userName");
		int studentId = Integer.parseInt(request.getParameter("studentId"));
		String userPassword = (String) request.getParameter("password");
		int gender = Integer.parseInt(request.getParameter("gender"));
		String webMail = (String) request.getParameter("webMail");
		String inputCode = (String) request.getParameter("inputCode");
		String rs = "실패";
		if(SendMail.codeMap.containsKey(webMail)) {
			if(SendMail.codeMap.get(webMail).equals(inputCode)) {
				rs = userDAO.join(studentId, userName, userPassword, gender, webMail) == 1 ? "성공" : "실패" ;
			}
		}
		response.getWriter().write(rs);
	}
}

package com.kkc.fcm;

import java.io.IOException;

import com.kkc.login.Login;
import com.kkc.sql.UserData;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FCMServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] parmas = new String[3];
		for(String key : request.getParameterMap().keySet()) {
			parmas = key.split("&");
			break;
		}
		
		int studentID = Integer.parseInt(parmas[0].split("=")[1]);
		String password = parmas[1].split("=")[1];
		String token = parmas[2].split("=")[1];
		try {
			UserData ud = Login.loginData.get(studentID);
			if(!ud.getPassword().equals(password))
				return;
			if(ud.getFCMToken() != token) {
				ud.setFCMToken(token);
				FirebaseClouldMessagingDAO dao = new FirebaseClouldMessagingDAO();
				dao.tokenUpdate(studentID, token);
			}
		}
		catch(NullPointerException e) {
			return;
		}
		response.getWriter().write("<script>");
		response.getWriter().write("location.href = '../../home/home.html'");
		response.getWriter().write("</script>");
	}

}

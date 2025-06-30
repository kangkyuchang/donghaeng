package com.kkc.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import com.kkc.sql.UserDAO;
import com.kkc.sql.UserData;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class Login extends HttpServlet {
	
	public static HashMap<Integer, UserData> loginData;
	private static final long serialVersionUID = 1L;
	
	static {
		loginData = new HashMap<Integer, UserData>();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDAO userDAO = new UserDAO();
		int userId = Integer.parseInt(request.getParameter("userId"));
		String password = request.getParameter("password");
		PrintWriter script = response.getWriter();
		UserData ud = userDAO.login(userId, password);
		if(ud != null) {
			script.write(getLoginJSON("success", ud));
			if(!loginData.containsKey(userId))
				loginData.put(userId, ud);
			System.out.println("성공");
		}
		else {
			response.getWriter().write(getLoginJSON("failure", ud));
			System.out.println("실패");
		}
	}
	
	public static String getLoginJSON(String result, UserData ud) {
		StringBuffer sb = new StringBuffer("");
		sb.append("{\"result\": ");
		if(ud != null) {
			sb.append("{\""+ result +"\": {");
			sb.append("\"studentID\": \"" + ud.getStudentID() + "\",");
			sb.append("\"name\": \"" + ud.getName() + "\",");
			sb.append("\"gender\": \"" + ud.getGender() + "\",");
			sb.append("\"webMail\": \"" + ud.getWebMail() + "\",");
			sb.append("\"password\": \"" + ud.getPassword() + "\",");
			sb.append("\"kindness\": \"" + ud.getKindness() + "\"}}");
		}
		else {
			sb.append("\"" + result +"\"");
		}
		sb.append("}");
		return sb.toString();
	}
	
	public static boolean checkLogin(int studentID, String password) {
		if(loginData.containsKey(studentID)) {
			UserData ud = loginData.get(studentID);
			if(ud.getPassword().equals(password))
				return true;
		}
		return false;
	}
}

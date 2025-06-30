package com.kkc.chat;

import java.io.IOException;
import java.util.HashMap;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ChatSelectServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	public static HashMap<Integer, Integer> csMap;
	
	static {
		csMap = new HashMap<Integer, Integer>();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("studentID") == null)
			return;
		int studentID = Integer.parseInt(request.getParameter("studentID"));
		
		if(request.getParameter("chatID") == null)
			return;
		int chatID = Integer.parseInt(request.getParameter("chatID"));
		csMap.put(studentID, chatID);
		response.getWriter().write("../chatting.html");
	}

}

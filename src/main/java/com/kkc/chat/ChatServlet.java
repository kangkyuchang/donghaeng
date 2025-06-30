package com.kkc.chat;

import java.io.IOException;

import com.kkc.login.Login;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ChatServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("studentID") == null || request.getParameter("content") == null 
				|| request.getParameter("dateTime") == null || request.getParameter("password") == null) {
			return;
		}
		
		int chatID = -1;
		int studentID = Integer.parseInt(request.getParameter("studentID"));
		String password = request.getParameter("password");
		if(!Login.checkLogin(studentID, password))
			return;
		if(ChatSelectServlet.csMap.containsKey(studentID)) 
			chatID = ChatSelectServlet.csMap.get(studentID);
		String content = request.getParameter("content");
		String dateTime = request.getParameter("dateTime");
		if(chatID == -1)
			return;
		
		Chat chat = new Chat();
		chat.setChatID(chatID);
		chat.setStudenID(studentID);
		chat.setName(Login.loginData.get(studentID).getName());
		chat.setContent(content);
		chat.setDateTime(dateTime);
		
		ChatDAO dao = new ChatDAO();
		int result = dao.chat(chat);
//		if(result == 1)
//			ChatUpdate.getInstance.setUpdate(chatID);
		response.getWriter().write("" + result);
	}

}

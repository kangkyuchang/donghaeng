package com.kkc.chat;

import java.io.IOException;
import java.util.List;

import com.kkc.login.Login;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoadChatServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("dateTime") == null || request.getParameter("studentID") == null
				|| request.getParameter("password") == null || request.getParameter("loadedChatNumber") == null)
			return;
		
		int studentID = Integer.parseInt(request.getParameter("studentID"));
		String password = request.getParameter("password");
		if(!Login.checkLogin(studentID, password))
			return;
		
		int chatID = -1;
		String dateTime = request.getParameter("dateTime");
		if(ChatSelectServlet.csMap.containsKey(studentID)) {
			chatID = ChatSelectServlet.csMap.get(studentID);
		}
		if(chatID == -1)
			return;
		
//		if(!ChatUpdate.getInstance.getUpdate(chatID, studentID)) {
//			response.getWriter().write("{\"chat_information\": []}");
//			return;
//		}
			
		int loadedChatNumber = Integer.parseInt(request.getParameter("loadedChatNumber"));
		ChatDAO dao = new ChatDAO();
		List<Chat> chatList = dao.loadChatDatas(chatID, dateTime, loadedChatNumber);
		if(chatList == null) {
			response.getWriter().write("{\"chat_information\": []}");
			return;
		}
		if(chatList.size() == 0) {
			response.getWriter().write("{\"chat_information\": []}");
			return;
		}
		response.getWriter().write(getJSON(chatList));
	}
	
	private String getJSON(List<Chat> chatList) {
		StringBuffer sb = new StringBuffer("");
		sb.append("{\"chat_information\": [");
		boolean first = true;
		for(Chat chat : chatList) {
			if(!first) 
				sb.append(", ");
			else
				first = false;
			sb.append("{");
			sb.append("\"chatNumber\": \"" + chat.getChatNumber() + "\",");
			sb.append("\"chatID\": \"" + chat.getChatID() + "\",");
			sb.append("\"studentID\": \"" + chat.getStudenID() + "\",");
			sb.append("\"name\": \"" + chat.getName() + "\",");
			sb.append("\"content\": \"" + chat.getContent() + "\",");
			sb.append("\"date\": \"" + chat.getDate() + "\",");
			sb.append("\"time\": \"" + chat.getTime() + "\"}");
		}
		sb.append("]}");
		return sb.toString();
	}

}

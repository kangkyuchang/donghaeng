package com.kkc.review;

import java.io.IOException;
import java.util.HashMap;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class SendReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashMap<Integer, Float> map = new HashMap<Integer, Float>();
		int senderID = 0;
		String password = null;
		int roomNumber = -1;
		for(String s : request.getParameterMap().keySet()) {
			if(s.equals("sender")) {
				senderID = Integer.parseInt(request.getParameter(s));
			}
			else if(s.equals("password"))
				password = request.getParameter(s);
			else if(s.equals("roomNumber")) {
				roomNumber = Integer.parseInt(request.getParameter(s));
			}
			else {
				int key = Integer.parseInt(s);
				float value = Float.parseFloat(request.getParameter(s));
				map.put(key, value);
			}
		}
		if(senderID == 0 || password == null || roomNumber == -1)
			return;
		ReviewDAO dao = new ReviewDAO();
		dao.updateReview(roomNumber, senderID, map);
	}
}

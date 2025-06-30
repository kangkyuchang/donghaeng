package com.kkc.register;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import com.kkc.sql.UserDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SendMail extends HttpServlet {
	public static HashMap<String, String> codeMap;
	static {
		codeMap = new HashMap<String, String>();
	}
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String code = "없음";
		String webMail = request.getParameter("webMail");
		if(webMail != null) {
			UserDAO userDAO = new UserDAO();
			int rs = userDAO.checkWebMailDuplication(webMail); // 1 있음, 0 없음, -1 오류
			if(rs == 0) {
				code = randomCode();
				codeMap.put(webMail, code);
			}
		}
		response.getWriter().write(code);
	}
	
	private String randomCode() {
		Random r = new Random();
		char[] alpha = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
		int numCount = 0;
		String code = "";
		for(int i = 0; i < 6; i++) {
			char random;
			boolean numSelection = false;
		    if(numCount < 2) {
		    	if(6-i <= 2 - numCount) {
		    		numSelection = true;
		    	}
		    	else {
		    		if(Math.random() < 0.5){
		    			numSelection = true;
					}
				}
			}
			if(numSelection) {
				random = Character.forDigit(r.nextInt(10), 10);
				numCount ++;
			}
			else {
				random = alpha[r.nextInt(alpha.length)];
			}
			code += String.valueOf(random);
		}
		return code;
	}
}

package com.kkc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.List;

import com.kkc.chat.Chat;
import com.kkc.chat.ChatDAO;
import com.kkc.chat.ChatSelectServlet;
import com.kkc.fcm.FCMServerSide;
import com.kkc.login.Login;
import com.kkc.sql.UserDAO;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.PongMessage;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/WebSocket")
public class MyWebSocket {

	private static HashMap<Integer, HashMap<Session, Integer>> ssMap;
	static {
		ssMap = new HashMap<Integer, HashMap<Session, Integer>>();
	}
	@OnOpen
	public void echoOpen(Session session) {
		String[] params = session.getQueryString().split("&");
		if(params.length == 2)
			return;
    	int studentID = Integer.parseInt(params[0].split("=")[1]);
    	String password = params[1].split("=")[1];
    	if(!Login.checkLogin(studentID, password)) {
    		return;
    	}
    	int chatID = ChatSelectServlet.csMap.get(studentID);
    	if(!ssMap.containsKey(chatID)) {
    		HashMap<Session, Integer> map = new HashMap<Session, Integer>();
    		map.put(session, studentID);
    		ssMap.put(chatID, map);
    	}
    	else {
    		HashMap<Session, Integer> map = ssMap.get(chatID);
        	if(!map.containsKey(session)) {
        		map.put(session, studentID);
        		ssMap.put(chatID, map);
        	}
    	}
	}

    @OnMessage
    public void echoTextMessage(Session session, String msg, boolean last) {
    	String[] split = msg.split(", ");
    	int studentID = Integer.parseInt(split[0].split(":")[1]);
    	String password = split[1].split(":")[1];
    	String dateTime = split[2].split(":")[1] + ":" + split[2].split(":")[2];
    	if(!Login.checkLogin(studentID, password))
    		return;
    	int chatID = ChatSelectServlet.csMap.get(studentID);
    	if(!ssMap.containsKey(chatID)) {
    		HashMap<Session, Integer> sessionMap = new HashMap<Session, Integer>();
    		sessionMap.put(session, studentID);
    		ssMap.put(chatID, sessionMap);
    	}
    	HashMap<Session, Integer> sessionMap = ssMap.get(chatID);
    	if(!sessionMap.containsKey(session)) {
    		sessionMap.put(session, studentID);
    		ssMap.put(chatID, sessionMap);
    	}
    	ChatDAO dao = new ChatDAO();
    	Chat chat = dao.getRecentChat(chatID, dateTime);
        try {
        	List<Integer> onlineMember = FCMServerSide.getInstance.getChattingMembers(chatID);
        	for(Session s : sessionMap.keySet()) {
        		if (s.isOpen()) { //클라이언트 측으로 다시 전송한다
                    s.getBasicRemote().sendText(getJSON(chat));
                    onlineMember.remove(sessionMap.get(s));
                }
        		else {
        			sessionMap.remove(s);
        		}
        	}
        	for(int i : onlineMember) {
        		UserDAO userDao = new UserDAO();
        		String token = userDao.getUserToken(i);
        		if(token != null)
        			FCMServerSide.getInstance.sendMessage(String.valueOf(studentID), chat.getContent(), token);
        	}
        } catch (IOException e) {

            try {
                session.close();
            } catch (IOException e1) {

            }

        }

    }



     // 바이너리 데이터를 수신할 때 호출됨

    @OnMessage

    public void echoBinaryMessage(Session session, ByteBuffer bb, boolean last) {

        try {

            if (session.isOpen()) {

            	session.getBasicRemote().sendText("파일이 서버에 도착했어요~", last);

                File file = new File("D:/test/sample.txt");

            	boolean append = false;



            	try {


            	    FileChannel wChannel = new FileOutputStream(file, append).getChannel();

            	    wChannel.write(bb);



//            	    wChannel.close();

            	} catch (IOException e) {

            	}

            }

        } catch (IOException e) {

            try {

                session.close();

            } catch (IOException e1) {

                // Ignore

            }

        }

    }

    @OnMessage
    public void echoPongMessage(PongMessage pm) {

    }

    

    @OnClose
    public void onClose() {
    	
    }

    

    @OnError

    public void onError(Session session, Throwable throwable) {

        // Error handling

    }
    
    private String getJSON(Chat chat) {
		StringBuffer sb = new StringBuffer("");
		sb.append("{\"chat_information\": [");
			sb.append("{");
			sb.append("\"chatNumber\": \"" + chat.getChatNumber() + "\",");
			sb.append("\"chatID\": \"" + chat.getChatID() + "\",");
			sb.append("\"studentID\": \"" + chat.getStudenID() + "\",");
			sb.append("\"name\": \"" + chat.getName() + "\",");
			sb.append("\"content\": \"" + chat.getContent() + "\",");
			sb.append("\"date\": \"" + chat.getDate() + "\",");
			sb.append("\"time\": \"" + chat.getTime() + "\"}");
		sb.append("]}");
		return sb.toString();
	}

}

package com.kkc.fcm;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.kkc.chat.ChatDAO;

public class FCMServerSide {

	public static FCMServerSide getInstance;
	private HashMap<Integer, List<Integer>> chatMap;
	
	static {
		getInstance = new FCMServerSide();
	}
	
	private FirebaseMessaging fcmInstance;
	
	private FCMServerSide() {
		init();
	}
	
	public void init() {
		chatMap = new HashMap<Integer, List<Integer>>();
		try {
			InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("donghaeng-server_key.json");
			FirebaseOptions options = new FirebaseOptions
											.Builder()
											.setCredentials(GoogleCredentials.fromStream(serviceAccount))
											.build();
			FirebaseApp app = FirebaseApp.initializeApp(options);
			fcmInstance = FirebaseMessaging.getInstance(app);
		}
		catch(Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
	public void sendMessage(String title, String content, String token) {
		Notification notification = Notification.builder().setTitle(title).setBody(content).build();
	    Message.Builder builder = Message.builder();
	    Message message = builder.setToken(token).setNotification(notification).build();
	    try {
	    	System.out.println("send");
			fcmInstance.send(message);
		} catch (FirebaseMessagingException e) {
			e.printStackTrace();
		}
	}
	
	public List<Integer> getChattingMembers(int chatNumber) {
		if(!chatMap.containsKey(chatNumber)) {
			ChatDAO dao = new ChatDAO();
			List<Integer> list = dao.getChattingMembers(chatNumber);
			if(list.size() < 1)
				return null;
			chatMap.put(chatNumber, list);
		}
		List<Integer> list = new ArrayList<Integer>();
		list.addAll(chatMap.get(chatNumber));
		return list;
	}
}

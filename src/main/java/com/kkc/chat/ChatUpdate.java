package com.kkc.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatUpdate {
	
	public static ChatUpdate getInstance;
	
	static {
		if(getInstance == null) {
			ChatUpdate cu = new ChatUpdate();
			getInstance = cu;
		}
	}
	
	private HashMap<Integer, List<Integer>> cuMap;
	
	private ChatUpdate() {
		cuMap = new HashMap<Integer, List<Integer>>();
	}
	
	public boolean getUpdate(int chatID, int studentID) {
		List<Integer> list = new ArrayList<Integer>();
		if(cuMap.containsKey(chatID)) {
			list = cuMap.get(chatID);
			if(list.contains(studentID)) {
				return false;
			}
		}
		list.add(studentID);
		cuMap.put(chatID, list);
		return true;
	}
	
	public void setUpdate(int chatID) {
		List<Integer> list = new ArrayList<Integer>();
		cuMap.put(chatID, list);
	}
}

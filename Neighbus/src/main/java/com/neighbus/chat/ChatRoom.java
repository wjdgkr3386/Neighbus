package com.neighbus.chat;

import java.util.UUID;

public class ChatRoom {
	
	private String roomId;
    private String roomName;

    public static ChatRoom create(String name) {
        ChatRoom room = new ChatRoom();
        room.roomId = UUID.randomUUID().toString();
        room.roomName = name;
        return room;
    }
}

package com.neighbus.friend;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FriendMapper {
	public String getMyUuid(int id);
	public int checkUuid(String uuid);
	
	public int checkFriendId(int id);
	public int checkFriend(@Param("myId") int myId, @Param("friendId") int friendId);
}

package com.neighbus.friend;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FriendMapper {
	//UUID가 존재하는지 확인
	public int checkUuid(String uuid);
	//UUID가 나인지 확인
	public int checkMyUuid(Map<String,Object> map);
	//이미 친추 요청을 했는지 확인
	public int checkFriendRequestByUuid(Map<String,Object> map);
	//이미 친추 요청을 했는지 확인
	public int checkFriendRequest(Map<String,Object> map);
	//이미 친구인지 확인
	public int checkFriend(Map<String,Object> map);
	//친구 요청
	public void friendRequest(Map<String,Object> map);
	//친구 추가
	public void addFriend(Map<String,Object> map);
	//친구 요청 상태 변경
	public void acceptFriendRequestState(Map<String,Object> map);
	//친구 요청 상태 변경
	public void refuseFriendRequestState(Map<String,Object> map);
	//친구 상태 삭제
	public void deleteFriendState(Map<String, Object> map);
	//친구 삭제
	public void deleteFriend(Map<String, Object> map);
	
}

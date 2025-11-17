package com.neighbus.mypage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class MyPageServiceImpl implements MyPageService { 

	// ⭐ @Autowired 필드 주입은 구현체 클래스에서만 가능합니다.
	@Autowired
	private MyPageMapper myPageMapper;

	// ⭐ 인터페이스의 메서드를 @Override하여 실제로 구현합니다.
	@Override
	public Map<String, Object> getMyPageInfo(String username) {
	    System.out.println("MyPageServiceimpl - getMyPageInfo");
	    return myPageMapper.getMyInfo(username);
	}

	@Override
	public List<Map<String, Object>> getMyPosts(String username) {
	    System.out.println("MyPageServiceimpl - getMyPosts");
	    return myPageMapper.getMyPosts(username);
	}

	@Override
	public List<Map<String, Object>> getMyComments(String username) {
	    System.out.println("MyPageServiceimpl - getMyComments");
	    return myPageMapper.getMyComments(username);
	}

	@Override
	public int getMyLikes(String username) {
	    System.out.println("MyPageServiceimpl - getMyLikes");
	    return myPageMapper.getMyLikesCount(username);
	}
	
	@Override
	public int addFriend(int id, String friendCode) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("friendCode", friendCode);
		if(myPageMapper.checkUser(map) < 1 || myPageMapper.checkFriend(map) >0) {
			return -1;
		}
		myPageMapper.addFriend(map);
		return 1;
	}

	@Override
	public void friendAccept(Map<String,Object> map) {
		myPageMapper.insertFriend(map);
		myPageMapper.updateFriendStateAccept(map);
	}

	@Override
	public void friendReject(Map<String,Object> map) {
		myPageMapper.updateFriendStateReject(map);
	}
}
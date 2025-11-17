package com.neighbus.mypage;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neighbus.account.AccountDTO;

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

}
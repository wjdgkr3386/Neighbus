package com.neighbus.mypage;

import java.util.List;
import java.util.Map;

import com.neighbus.account.AccountDTO;

public interface MyPageService {

	public AccountDTO getMyPageInfo(String username);

	public List<Map<String, Object>> getMyPosts(String username);

	public List<Map<String, Object>> getMyComments(String username);

	public int getMyLikes(String username);

}
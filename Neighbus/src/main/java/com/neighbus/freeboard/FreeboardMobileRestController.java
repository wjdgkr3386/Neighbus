package com.neighbus.freeboard;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neighbus.account.AccountDTO;


@RestController
@RequestMapping("/api/mobile/freeboard")
public class FreeboardMobileRestController {

	@GetMapping("/getBoards")
	public ResponseEntity<Map<String,Object>> getBoards(
		@AuthenticationPrincipal AccountDTO user
	){
		
		return null;
	}
}

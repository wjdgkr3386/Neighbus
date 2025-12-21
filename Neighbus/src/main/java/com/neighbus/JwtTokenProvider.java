package com.neighbus;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    // 실제 운영 시에는 환경변수나 Secret 값을 사용 권장
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long tokenValidity = 1000L * 60 * 60 * 10; // 10시간

    @Autowired
    private UserDetailsService userDetailsService;

    // 토큰 생성
    public String createToken(Authentication authentication) {
        return Jwts.builder()
                .setSubject(authentication.getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + tokenValidity))
                .signWith(key)
                .compact();
    }

    // 인증 객체 생성
    public Authentication getAuthentication(String token) {
    	String username = this.getUsername(token);
    	UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    	return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 username 추출
    public String getUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    // 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
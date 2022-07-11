package com.mpnp.baechelin.oauth.token;

import com.mpnp.baechelin.oauth.exception.TokenValidFailedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
public class AuthTokenProvider {

    private final Key key;
    private static final String AUTHORITIES_KEY = "role";

    public AuthTokenProvider(String secret) {
        // jwt secret key. 키를 byte 배열로 변환한 후, key 객체로 변환한다.
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    // refresh token 생성
    public AuthToken createAuthToken(String id, Date expiry) {
        return new AuthToken(id, expiry, key);
    }

    // access token 생성
    public AuthToken createAuthToken(String id, String role, Date expiry) {
        return new AuthToken(id, role, expiry, key);
    }

    // header 로 들어온 String 형태의 access token 을 AuthToken 형태로 변환
    public AuthToken convertAuthToken(String token) {
        return new AuthToken(token, key);
    }


    // 인증 객체 생성
    public Authentication getAuthentication(AuthToken authToken) {
        // 유효한 토큰일 때
        if (authToken.validate()) {
            Claims claims = authToken.getTokenClaims();
            Collection<? extends GrantedAuthority> authorities =
                    Arrays.stream(new String[] {claims.get(AUTHORITIES_KEY).toString()})
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());
            log.debug("claims subject : [{}]", claims.getSubject());
            User principal = new User(claims.getSubject(), "", authorities); // userDetils 의 user 객체. username, password, authorities 세팅

            // 인증이 끝나고 SecurityContextHolder.getContext에 등록될 Authentication(인증) 객체
            return new UsernamePasswordAuthenticationToken(principal, authToken, authorities);
        } else {
            throw new TokenValidFailedException();
        }
    }
}

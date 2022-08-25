package com.example.week3.Security;

import com.example.week3.Dto.TokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component// 1.Bean 생성
public class TokenProvider implements InitializingBean {
    private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
    private static final String AUTHORITIES_KEY = "auth";
    private final String secret;
    private final String refresh;
    private final long tokenValidityInMilliseconds;
    private final long rTokenValidityInMilliseconds;
    private Key key;

    // 2. 의존성 주입
    public TokenProvider(
            @Value("${jwt.secret}")
            String secret,
            @Value("${jwt.refresh}")
            String refresh,
            @Value("${jwt.token-validity-in-seconds}")
            long tokenValidityInSeconds

    ) {
        this.secret = secret;
        this.refresh = refresh;
        this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
        this.rTokenValidityInMilliseconds = tokenValidityInMilliseconds*2000;
    }

    // 3. 주입받은 secret 값을 Base64 Decode해서 key변수에 할당
    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenDto createToken(Authentication authentication){
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();

        String accessToken = Jwts.builder() // access 토큰 생성
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(new Date(now + this.tokenValidityInMilliseconds))
                .compact();

        String refreshToken = Jwts.builder() // access 토큰보다 유지시간이 2배길고 아무내용이 담기지않은 refresh토큰 생성
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(new Date(now + this.rTokenValidityInMilliseconds))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();


        return new TokenDto(accessToken, refreshToken);
    }


    // 권한 가져오기
    public Authentication getAuthentication(String token) {
        // token 복호화
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        // token에서 권한에 관한 정보 추출
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // user 객체에 담아서 리턴
        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    //토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            logger.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            logger.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            logger.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            logger.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}
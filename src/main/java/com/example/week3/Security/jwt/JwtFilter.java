package com.example.week3.Security.jwt;

import com.example.week3.Security.TokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Collection;

public class JwtFilter extends GenericFilterBean {

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
    public static final String AUTHORIZATION_HEADER = "Access-token";
    public static final String AUTHORIZATION_HEADER2 = "Refresh-token";
    private TokenProvider tokenProvider;
    public JwtFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override //토큰의 인증정보를 SecurutyContext에 저장
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String jwt = resolveToken(httpServletRequest);
        System.out.println(jwt);
        String requestURI = httpServletRequest.getRequestURI();
        System.out.println(requestURI);

        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            Authentication authentication = tokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Collection role=authentication.getAuthorities();
            if (role.contains("admin")){
                System.out.println("ADMIN");
            }
            logger.debug("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
        } else {
            logger.debug("유효한 JWT 토큰이 없습니다, uri: {}", requestURI);
            //throw new RuntimeException("토큰이 유효하지않습니다.");
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    //토큰정보 꺼내오기
    private String resolveToken(HttpServletRequest request) {
        //헤더에서 "Access-token" 의 value를 추출
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        //추출한 값이 유효한 값인지 확인후 리턴
        if (StringUtils.hasText(bearerToken)) {

            return bearerToken;
        }
        return bearerToken;
    }
}
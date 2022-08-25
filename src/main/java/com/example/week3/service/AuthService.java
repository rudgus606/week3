package com.example.week3.Service;

import com.example.week3.Dto.LoginDto;
import com.example.week3.Dto.SignUpDto;
import com.example.week3.Dto.TokenDto;
import com.example.week3.Entity.Authority;
import com.example.week3.Entity.User;
import com.example.week3.Repository.UserRepository;
import com.example.week3.Security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final TokenProvider tokenProvider;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    //로그인
    @Transactional
    public TokenDto login(LoginDto loginDto){
        UsernamePasswordAuthenticationToken authenticationToken = loginDto.toAuthentication();

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // Access&Refresh 토큰 생성
        TokenDto tokenDto = tokenProvider.createToken(authentication);

        return tokenDto;
    }

    public String signup(SignUpDto signUpDto){
        String msg;
        if(userRepository.existsByNickname(signUpDto.getNickname())){
            throw new RuntimeException("중복된 닉네임 입니다.");
        }
        else if (!signUpDto.getPassword().equals(signUpDto.getPasswordConfirm())){
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
        User newUser= User.builder()
                .nickname(signUpDto.getNickname())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .activated(true)
                .build();
        userRepository.save(newUser);
        msg = "가입완료";
        return msg;


    }
}
package com.example.week3.Service;

import com.example.week3.Entity.User;
import com.example.week3.Repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component("userDetailsService")
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;


    //1.유저정보 주입
    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }


    //2. 주입된 유저정보를 기반으로 DB에서 유저정보와 권한정보를 받아
    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String nickname) {
        return userRepository.findOneWithAuthoritiesByNickname(nickname)
                .map(user -> createUser(nickname, user))
                .orElseThrow(() -> new UsernameNotFoundException(nickname + " -> 데이터베이스에서 찾을 수 없습니다."));
    }

    private org.springframework.security.core.userdetails.User createUser(String username, User user) {
        if (!user.isActivated()) {
            throw new RuntimeException(username + " -> 활성화되어 있지 않습니다.");
        }

        List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getNickname(),
                user.getPassword(),
                grantedAuthorities);
    }

    public String NickName(){
        String nickName= SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("->"+nickName);
        return nickName;
    }

    public String MyDetail(){
        String MyDetail= SecurityContextHolder.getContext().getAuthentication().toString();
        return MyDetail;
    }

    public String MyAuth(){
        String MyAuth= SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        return MyAuth;
    }
}
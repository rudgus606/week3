package com.example.week3.Entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity // DB의 테이블과 1:1 매핑되는 객체임을 뜻함
@Table(name = "users") // Table이름 지정
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "nickname", length = 50, unique = true)
    private String nickname;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "activated")
    private boolean activated;

    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;

}
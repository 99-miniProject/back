package com.sparta.camp.controller;

import com.sparta.camp.domain.User;
import com.sparta.camp.dto.SignupDto;
import com.sparta.camp.repository.UserRepository;
import com.sparta.camp.security.JwtTokenProvider;
import com.sparta.camp.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입 url
    @PostMapping("/signup")
    public String createUser(@RequestBody SignupDto signupDto) throws IllegalAccessException {
        return userService.createUser(signupDto);
    }

    // 로그인 url
    @PostMapping("/login")
    public List<Map<String,String>> login(@RequestBody SignupDto signupDto) {
        User user = userRepository.findByUsername(signupDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 유저입니다."));
        if (!passwordEncoder.matches(signupDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        // 토큰 찾아오기
        Map<String,String> username =new HashMap<>();
        Map<String,String>token = new HashMap<>();
        List<Map<String,String>> tu = new ArrayList<>(); // -> 리스트를 만드는데, Map형태(키:밸류 형태)의 변수들을 담을 것이다.

        token.put("token",jwtTokenProvider.createToken(signupDto.getUsername())); // "username" : {username}
        username.put("username",user.getUsername()); // "token" : {token}
        tu.add(username); //List형태 ["username" : {username}]
        tu.add(token); //List형태 ["token" : {token}]
        return tu; // List형태 ["username" : {username}, "token" : {token}]
    }
}



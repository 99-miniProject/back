package com.sparta.camp.controller;

import com.sparta.camp.domain.User;
import com.sparta.camp.dto.SignupDto;
import com.sparta.camp.repository.UserRepository;
import com.sparta.camp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public String createUser(@RequestBody SignupDto signupDto) throws IllegalAccessException {
        return userService.createUser(signupDto);
    }
    @PostMapping("/login")
    public void login(@RequestBody SignupDto signupDto) {
        User user = userRepository.findByUsername(signupDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 유저입니다."));
        if (!passwordEncoder.matches(signupDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
    }

}

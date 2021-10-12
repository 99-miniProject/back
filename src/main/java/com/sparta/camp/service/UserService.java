package com.sparta.camp.service;

import com.sparta.camp.domain.User;
import com.sparta.camp.dto.SignupDto;
import com.sparta.camp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public String createUser(SignupDto signupDto) throws IllegalAccessException {
        String username = signupDto.getUsername();
        String password = signupDto.getPassword();
        String passwordChk = signupDto.getPasswordChk();
        String nickname = signupDto.getNickname();

        Optional<User> check = userRepository.findByUsername(username);
        String pattern = "^[a-zA-Z0-9]*$";

        if(check.isPresent()){
            throw new IllegalAccessException("중복된 아이디가 존재합니다");
        }else if(username.length() < 5){
            throw new IllegalAccessException("아이디는 5자 이상으로 입력하세요");
        }else if(!password.equals(passwordChk)) {
            throw new IllegalAccessException("비밀번호가 맞지 않습니다");
        }else if(password.length() < 8){
            throw new IllegalAccessException("비밀번호는 8자 이상으로 입력하세요");
        }else if (!Pattern.matches(pattern, username)) {
            throw new IllegalAccessException("아이디는 알파벳 대소문자와 숫자로만 입력하세요");
        }else if(nickname.length() < 2){
            throw new IllegalAccessException("닉네임은 2자 이상으로 입력하세요");
        }


        if (!signupDto.getPassword().equals(signupDto.getPassword())){
            throw new IllegalAccessException("비밀번호가 일치하지 않습니다");
        }else{
            signupDto.setPassword(bCryptPasswordEncoder.encode(signupDto.getPassword()));
            User user = new User(signupDto);
            userRepository.save(user);
            return "회원가입이 완료되었습니다";
        }

    }
}

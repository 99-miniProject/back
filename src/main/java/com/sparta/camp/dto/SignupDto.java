package com.sparta.camp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupDto {
    private String username;
    private String nickname;
    private String password;
    private String passwordChk;
}

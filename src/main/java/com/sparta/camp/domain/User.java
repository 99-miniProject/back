package com.sparta.camp.domain;

import com.sparta.camp.dto.SignupDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@ToString
@Getter
@NoArgsConstructor
@Entity
public class User extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String nickname;

    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Review> reviewList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Reservation> reservationList;

    public User(SignupDto signupDto) {
        this.username = signupDto.getUsername();
        this.password = signupDto.getPassword();
        this.nickname = signupDto.getNickname();
    }
}

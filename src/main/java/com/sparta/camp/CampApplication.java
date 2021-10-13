package com.sparta.camp;

import com.sparta.camp.domain.Camp;
import com.sparta.camp.domain.User;
import com.sparta.camp.repository.CampRepository;
import com.sparta.camp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.TimeZone;

@EnableJpaAuditing
@SpringBootApplication
public class CampApplication {

    public static void main(String[] args) {
        SpringApplication.run(CampApplication.class, args);
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }

}

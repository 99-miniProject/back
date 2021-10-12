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

@EnableJpaAuditing
@SpringBootApplication
public class CampApplication {

    public static void main(String[] args) {
        SpringApplication.run(CampApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(CampRepository campRepository, UserRepository userRepository) {
        return (args) -> {
            userRepository.save(new User("kwon", "kwon", "1234"));
            userRepository.save(new User("kim", "kim", "1234"));
            userRepository.save(new User("lee", "lee", "1234"));
            campRepository.save(new Camp("A캠핑장", 130000, "welcome!", 4, "url"));
            campRepository.save(new Camp("B캠핑장", 50000, "welcome!", 4, "url"));
            campRepository.save(new Camp("C캠핑장", 240000, "welcome!", 4, "url"));
            campRepository.save(new Camp("D캠핑장", 170000, "welcome!", 4, "url"));
            campRepository.save(new Camp("E캠핑장", 140000, "welcome!", 4, "url"));
            campRepository.save(new Camp("F캠핑장", 60000, "welcome!", 4, "url"));
        };
    }

}

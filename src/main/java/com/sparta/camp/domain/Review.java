package com.sparta.camp.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@ToString(exclude = {"user", "camp"})
@Getter
@NoArgsConstructor
@Entity
public class Review extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonManagedReference
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonManagedReference
    @JoinColumn(name = "camp_id")
    private Camp camp;

    private String content;

    public Review(User user, Camp camp, String content) {
        this.user = user;
        this.camp = camp;
        this.content = content;
    }

    public void changeContent(String content) {
        this.content = content;
    }
}

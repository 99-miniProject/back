package com.sparta.camp.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@ToString
@Getter
@NoArgsConstructor
@Entity
public class Camp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "camp_id")
    private Long id;

    private String name;

    private int price;

    private String info;

    private int capacity;

    private String img;

    @OneToMany(mappedBy = "camp", cascade = CascadeType.ALL)
    private List<Review> reviewList;

    @OneToMany(mappedBy = "camp", cascade = CascadeType.ALL)
    private List<Reservation> reservationList;
}

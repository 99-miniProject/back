package com.sparta.camp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonBackReference
    @JsonIgnore
    private List<Review> reviewList;

    @OneToMany(mappedBy = "camp", cascade = CascadeType.ALL)
    @JsonBackReference
    @JsonIgnore
    private List<Reservation> reservationList;

    public Camp(String name, int price, String info, int capacity, String img) {
        this.name = name;
        this.price = price;
        this.info = info;
        this.capacity = capacity;
        this. img = img;
    }
}

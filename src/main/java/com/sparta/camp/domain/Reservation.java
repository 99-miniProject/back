package com.sparta.camp.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sparta.camp.dto.ReservationRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@ToString(exclude = {"user", "camp"})
@Getter
@NoArgsConstructor
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonManagedReference
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonManagedReference
    @JoinColumn(name = "camp_id")
    private Camp camp;

    private int count;

    private LocalDateTime checkinDate;

    public Reservation(User user, Camp camp, int count, LocalDateTime checkinDate) {
        this.user = user;
        this.camp = camp;
        this.count = count;
        this.checkinDate = checkinDate;
    }

    public void changeCount(int count) {
        this.count = count;
    }

    public void changeCheckinDate(LocalDateTime checkinDate) {
        this.checkinDate = checkinDate;
    }

}

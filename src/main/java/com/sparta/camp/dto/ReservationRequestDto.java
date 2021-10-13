package com.sparta.camp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class ReservationRequestDto {

    private Long campId;

    private int count;

    private LocalDateTime checkinDate;

}
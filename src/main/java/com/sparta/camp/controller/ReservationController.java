package com.sparta.camp.controller;

import com.sparta.camp.domain.Reservation;
import com.sparta.camp.dto.ReservationRequestDto;
import com.sparta.camp.security.UserDetailsImpl;
import com.sparta.camp.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ReservationController {

    private final ReservationService service;

    // 예약 생성
    @PostMapping("/books")
    public Reservation create(@RequestBody ReservationRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Long userId = userDetails.getUser().getId();

        Reservation reservation = service.create(requestDto, userId);

        log.info("reservation = {}", reservation);

        return reservation;
    }

    // 예약 조회
    @GetMapping("/mypage")
    public List<Reservation> getList(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        Long userId = userDetails.getUser().getId();

        return service.getList(userId);
    }

    // 예약 수정
    @PutMapping("/books/{reservationId}")
    public Reservation update(@PathVariable Long reservationId, @RequestBody ReservationRequestDto requestDto) {

        return service.update(requestDto, reservationId);
    }

    // 예약 삭제
    @DeleteMapping("/books/{reservationId}")
    public String delete(@PathVariable Long reservationId) {

        service.delete(reservationId);

        return "예약 취소 성공!";
    }

}

package com.sparta.camp.service;

import com.sparta.camp.domain.Camp;
import com.sparta.camp.domain.Reservation;
import com.sparta.camp.domain.User;
import com.sparta.camp.dto.ReservationRequestDto;
import com.sparta.camp.repository.CampRepository;
import com.sparta.camp.repository.ReservationRepository;
import com.sparta.camp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final CampRepository campRepository;

    // 예약 생성
    public Reservation create(ReservationRequestDto requestDto) {

        User user = userRepository.findById(requestDto.getUserId()).orElseThrow(
                () -> new NullPointerException("존재하지 않는 회원입니다.")
        );

        Camp camp = campRepository.findById(requestDto.getCampId()).orElseThrow(
                () -> new NullPointerException("존재하지 않는 캠핑장입니다.")
        );

        int count = requestDto.getCount();

        LocalDateTime checkinDate = requestDto.getCheckinDate();

        Reservation reservation = new Reservation(user, camp, count, checkinDate);

        return reservationRepository.save(reservation);

    }

    // 예약 조회
    public List<Reservation> getList(Long userId) {

        return reservationRepository.findAllByUserId(userId);
    }

    // 예약 수정
    public Reservation update(ReservationRequestDto requestDto, Long reservationId) {

        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(
                () -> new NullPointerException("존재하지 않는 예약입니다.")
        );

        reservation.changeCount(requestDto.getCount());
        reservation.changeCheckinDate(requestDto.getCheckinDate());

        return reservationRepository.save(reservation);
    }

    // 예약 삭제
    public void delete(Long reservationId) {

        reservationRepository.deleteById(reservationId);
    }

}

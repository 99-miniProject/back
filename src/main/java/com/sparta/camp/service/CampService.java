package com.sparta.camp.service;

import com.sparta.camp.domain.Camp;
import com.sparta.camp.repository.CampRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CampService {

    private final CampRepository campRepository;

    // 목록 조회
    public List<Camp> getList() {

        return campRepository.findAll();
    }

    // 상세 조회
    public Camp read(Long campId) {

        return campRepository.findById(campId).orElseThrow(
                () -> new NullPointerException("존재하지 않는 캠핑장입니다.")
        );
    }

}

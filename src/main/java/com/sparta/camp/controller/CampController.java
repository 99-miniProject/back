package com.sparta.camp.controller;

import com.sparta.camp.domain.Camp;
import com.sparta.camp.service.CampService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CampController {

    private final CampService service;

    // 목록 조회
    @GetMapping("/camps")
    public List<Camp> getList() {

        return service.getList();
    }

    // 상세 조회
    @GetMapping("/camps/{campId}")
    public Camp read(@PathVariable Long campId) {

        return service.read(campId);
    }

}

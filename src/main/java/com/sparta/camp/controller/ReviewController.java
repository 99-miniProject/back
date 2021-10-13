package com.sparta.camp.controller;

import com.sparta.camp.domain.Review;
import com.sparta.camp.dto.ReviewRequestDto;
import com.sparta.camp.security.UserDetailsImpl;
import com.sparta.camp.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ReviewController {

    private final ReviewService service;

    // 리뷰 작성
    @PostMapping("/reviews")
    public Review create(@RequestBody ReviewRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Long userId = userDetails.getUser().getId();

        return service.create(requestDto, userId);
    }

    // 리뷰 조회
    @GetMapping("/reviews/{campId}")
    public List<Review> getList(@PathVariable Long campId) {

        return service.getList(campId);
    }

    // 리뷰 수정
    @PutMapping("/reviews/{reviewId}")
    public Review update(@PathVariable Long reviewId, @RequestBody ReviewRequestDto requestDto) {

        return service.update(requestDto, reviewId);
    }

    // 리뷰 삭제
    @DeleteMapping("/reviews/{reviewId}")
    public String delete(@PathVariable Long reviewId) {

        service.delete(reviewId);

        return "리뷰 삭제 성공!";
    }

}

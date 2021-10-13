package com.sparta.camp.service;

import com.sparta.camp.domain.Camp;
import com.sparta.camp.domain.Review;
import com.sparta.camp.domain.User;
import com.sparta.camp.dto.ReviewRequestDto;
import com.sparta.camp.repository.CampRepository;
import com.sparta.camp.repository.ReviewRepository;
import com.sparta.camp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final CampRepository campRepository;

    // 리뷰 작성
    public Review create(ReviewRequestDto requestDto, Long userId) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new NullPointerException("존재하지 않는 회원입니다.")
        );

        Camp camp = campRepository.findById(requestDto.getCampId()).orElseThrow(
                () -> new NullPointerException("존재하지 않는 캠핑장입니다.")
        );

        String content = requestDto.getContent();

        Review review = new Review(user, camp, content);

        return reviewRepository.save(review);

    }

    // 리뷰 조회
    public List<Review> getList(Long campId) {

        return reviewRepository.findAllByCampId(campId);
    }

    // 리뷰 수정
    public Review update(ReviewRequestDto requestDto, Long reviewId) {

        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new NullPointerException("존재하지 않는 리뷰입니다.")
        );

        review.changeContent(requestDto.getContent());

        return reviewRepository.save(review);
    }

    // 리뷰 삭제
    public void delete(Long reviewId) {

        reviewRepository.deleteById(reviewId);
    }

}

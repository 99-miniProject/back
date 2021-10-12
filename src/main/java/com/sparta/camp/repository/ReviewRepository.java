package com.sparta.camp.repository;

import com.sparta.camp.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByCampId(Long campId);
}

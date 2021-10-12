package com.sparta.camp.repository;

import com.sparta.camp.domain.Camp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampRepository extends JpaRepository<Camp, Long> {
}

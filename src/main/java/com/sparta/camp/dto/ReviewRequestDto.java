package com.sparta.camp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class ReviewRequestDto {

    private Long userId;

    private Long campId;

    private String content;
}

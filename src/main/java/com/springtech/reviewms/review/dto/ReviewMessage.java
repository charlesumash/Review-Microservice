package com.springtech.reviewms.review.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewMessage {
    private double companyRating;
    private Long companyId;
}

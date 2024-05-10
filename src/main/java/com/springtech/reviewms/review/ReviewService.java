package com.springtech.reviewms.review;

import java.util.List;

public interface ReviewService {

    public List<Review> getAllReviews(Long companyId);

    public Boolean addReview(Long companyId, Review review);

    public Review getReviewById(Long reviewId);

    public Boolean updateReview(Long reviewId, Review updatedReview);

    public Boolean deleteReviewById(Long reviewId);
}

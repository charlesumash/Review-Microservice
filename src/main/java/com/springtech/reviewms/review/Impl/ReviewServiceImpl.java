package com.springtech.reviewms.review.Impl;

import com.springtech.reviewms.review.Review;
import com.springtech.reviewms.review.ReviewRepository;
import com.springtech.reviewms.review.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public List<Review> getAllReviews(Long companyId) {
        return reviewRepository.findAllByCompanyId(companyId);
    }

    @Override
    public Boolean addReview(Long companyId, Review review) {
        if (companyId != null){
            review.setCompanyId(companyId);
            reviewRepository.save(review);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Review getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElse(null);
    }

    @Override
    public Boolean updateReview(Long reviewId, Review updatedReview) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if (review != null){
            updatedReview.setReviewId(reviewId);
            updatedReview.setCompanyId(review.getCompanyId());
            reviewRepository.save(updatedReview);
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteReviewById(Long reviewId) {
        if (reviewRepository.existsById(reviewId)){
            reviewRepository.deleteById(reviewId);
            return true;
        }
        return false;
    }
}

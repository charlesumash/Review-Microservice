package com.springtech.reviewms.review;

import com.springtech.reviewms.review.messaging.ReviewMessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewMessageProducer reviewMessageProducer;

    @GetMapping
    public ResponseEntity<List<Review>> findAllByCompanyId(@RequestParam Long companyId){
        return new ResponseEntity<>(reviewService.getAllReviews(companyId),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addReview(@RequestParam Long companyId,
                                            @RequestBody Review review){
        boolean isReviewSaved = reviewService.addReview(companyId, review);
        if (isReviewSaved){
            getAverageRating(companyId);
            return new ResponseEntity<>("Review successfully added", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Review not saved", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long reviewId){
        Review review = reviewService.getReviewById(reviewId);
        if (review != null){
            return new ResponseEntity<>(review, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReviewById(@PathVariable Long reviewId,
                                                   @RequestBody Review updatedReview){
        Boolean isReviewUpdated = reviewService.updateReview(reviewId, updatedReview);
        if (isReviewUpdated){
            return new ResponseEntity<>("Review successfully updated", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Review not updated", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReviewById(@PathVariable Long reviewId){
        Boolean isReviewDeleted = reviewService.deleteReviewById(reviewId);
        if (isReviewDeleted){
            return new ResponseEntity<>("Review successfully deleted", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Review not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/averageRating")
    public void getAverageRating(Long companyId){
        List<Review> reviewList = reviewService.getAllReviews(companyId);
        Double companyRating = reviewList.stream()
                .mapToDouble(Review::getRating).average().orElse(0.0);
        companyRating = Math.ceil(companyRating * 10) / 10.0;
        reviewMessageProducer.sendMessage(companyRating, companyId);
    }
}

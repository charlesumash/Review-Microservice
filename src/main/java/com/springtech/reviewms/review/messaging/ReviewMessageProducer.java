package com.springtech.reviewms.review.messaging;

import com.springtech.reviewms.review.Review;
import com.springtech.reviewms.review.dto.ReviewMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewMessageProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(Double companyRating, Long companyId){
        ReviewMessage reviewMessage = new ReviewMessage();


        reviewMessage.setCompanyRating(companyRating);
        reviewMessage.setCompanyId(companyId);

        rabbitTemplate.convertAndSend("companyRatingQueue", reviewMessage);
    }
}

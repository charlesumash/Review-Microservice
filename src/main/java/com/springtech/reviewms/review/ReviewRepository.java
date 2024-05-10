package com.springtech.reviewms.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query(value = "select * from reviews where company_id = :companyId", nativeQuery = true)
    public List<Review> findAllByCompanyId(Long companyId);
}
